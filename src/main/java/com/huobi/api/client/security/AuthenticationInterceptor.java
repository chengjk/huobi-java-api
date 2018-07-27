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
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * A request interceptor that injects the API Key into requests, and signs messages, whenever required.
 */
public class AuthenticationInterceptor implements Interceptor {
    private final String apiKey;
    private final String secret;

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
        if (original.method().equalsIgnoreCase("post")) {
            newRequestBuilder.addHeader("Content-Type", "application/json");
        } else if (original.method().equalsIgnoreCase("get")) {
            newRequestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        newRequestBuilder.addHeader("Language", "zh-cn");
        String gmtNow = gmtNow();

        //add params if required
        if (isApiKeyRequired || isSignatureRequired) {
            String signature = createSignature(original.method(), original.url(), gmtNow);
            HttpUrl signedUrl = original.url().newBuilder()
                    .addQueryParameter("AccessKeyId", apiKey)
                    .build();
            if (isSignatureRequired) {
                //append signature
                signedUrl = signedUrl.newBuilder()
                        .addQueryParameter("SignatureMethod", HuobiConsts.SIGNATURE_METHOD)
                        .addQueryParameter("SignatureVersion", HuobiConsts.SIGNATURE_VERSION)
                        .addQueryParameter("Timestamp", gmtNow)
                        .addQueryParameter("Signature", signature)
                        .build();
            }
            newRequestBuilder.url(signedUrl);
        }
        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }


    /**
     * 创建签名.
     * https://github.com/huobiapi/API_Docs/wiki/REST_authentication
     *
     * @param method
     * @param request
     * @param gmtNow
     * @return
     */
    private String createSignature(String method, HttpUrl request, String gmtNow) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(HuobiConsts.API_HOST.toLowerCase()).append('\n') // Host
                .append(request.uri().getPath()).append('\n'); // /path


        StringJoiner joiner = new StringJoiner("&");
        joiner.add("AccessKeyId=" + apiKey)
                .add("SignatureMethod=" + HuobiConsts.SIGNATURE_METHOD)
                .add("SignatureVersion=" + HuobiConsts.SIGNATURE_VERSION)
                .add("Timestamp=" + urlEncode(gmtNow));


        //参数排序
        TreeSet<String> names = new TreeSet(request.queryParameterNames());


        //拼接
        for (String key : names) {
            String value = request.queryParameter(key);
            joiner.add(key + '=' + urlEncode(value));
        }
        return HmacSHA256Signer.sign(sb.toString() + joiner.toString(), secret);
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


    private String gmtNow() {
        return Instant.now().atZone(HuobiConsts.ZONE_GMT).format(HuobiConsts.DT_FORMAT);
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