package com.huobi.api.client.security;

import com.huobi.api.client.constant.HuobiConsts;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A request interceptor that injects the API Key into requests, and signs messages, whenever required.
 */
public class AuthenticationInterceptor implements Interceptor {
    private final String apiKey;
    private final String secret;


    static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    static final ZoneId ZONE_GMT = ZoneId.of("Z");

    public AuthenticationInterceptor(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder newRequestBuilder = original.newBuilder();

        boolean isApiKeyRequired = original.header(HuobiConsts.ENDPOINT_SECURITY_TYPE_APIKEY) != null;
        boolean isSignatureRequired = original.header(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED) != null;

        //add headers
        newRequestBuilder.addHeader("User-Agent", HuobiConsts.USER_AGENT);
        newRequestBuilder.addHeader("Content-Type", "application/json");


        //add params if required
        if (isApiKeyRequired || isSignatureRequired) {
            HttpUrl signedUrl = original.url().newBuilder()
                    .addQueryParameter("AccessKeyId", apiKey)
                    .build();
            if (isSignatureRequired) {
                signedUrl = signedUrl.newBuilder()
                        .addQueryParameter("SignatureMethod", HuobiConsts.SIGNATURE_METHOD)
                        .addQueryParameter("SignatureVersion", HuobiConsts.SIGNATURE_VERSION)
                        .addEncodedQueryParameter("Timestamp", gmtNow())
                        .build();

                //append signature
                signedUrl = signedUrl.newBuilder()
                        .addQueryParameter("Signature", createSign(original.method(), signedUrl))
                        .build();
            }
            newRequestBuilder.url(signedUrl);
        }

        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }

    private String createSign(String method, HttpUrl request) {

        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(HuobiConsts.API_HOST.toLowerCase()).append('\n') // Host
                .append(request.uri().getPath()).append('\n'); // /path
        //参数排序
        TreeSet<String> names = new TreeSet(request.queryParameterNames());
        //拼接
        for (String key : names) {
            String value = request.queryParameter(key);
            sb.append(key).append('=').append(urlEncode(value)).append('&');
        }
        return HmacSHA256Signer.sign(sb.toString(), secret);
    }


    /**
     * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不是+。
     *
     * @param s String字符串
     * @return URL编码后的字符串
     */
    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    /**
     * Return epoch seconds
     */
    private long epochNow() {
        return Instant.now().getEpochSecond();
    }

    private String gmtNow() {
        return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthenticationInterceptor that = (AuthenticationInterceptor) o;
        return Objects.equals(apiKey, that.apiKey) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, secret);
    }
}