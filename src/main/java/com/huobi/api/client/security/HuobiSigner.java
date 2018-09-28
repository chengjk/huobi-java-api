package com.huobi.api.client.security;

import com.huobi.api.client.constant.HuobiConsts;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/**
 * created by jacky. 2018/9/27 2:34 PM
 */
public class HuobiSigner {
    private String apiKey;
    private String secret;
    public HuobiSigner(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * 创建签名.
     * https://github.com/huobiapi/API_Docs/wiki/REST_authentication
     *
     * @param method
     * @param params
     * @param gmtNow
     * @return
     */
    public String sign(String method, String uri, Map<String, String> params, String gmtNow) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(HuobiConsts.API_HOST.toLowerCase()+":443").append('\n') // Host
                .append(uri).append('\n'); // /path

        StringJoiner joiner = new StringJoiner("&");
        joiner.add("AccessKeyId=" + apiKey)
                .add("SignatureMethod=" + HuobiConsts.SIGNATURE_METHOD)
                .add("SignatureVersion=" + HuobiConsts.SIGNATURE_VERSION)
                .add("Timestamp=" + urlEncode(gmtNow));

        //参数排序
        TreeSet<String> names = new TreeSet(params.keySet());

        //拼接
        for (String key : names) {
            String value = params.get(key);
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


    public String gmtNow() {
        return Instant.now().atZone(HuobiConsts.ZONE_GMT).format(HuobiConsts.DT_FORMAT);
    }



    //////--------------
    public static void main(String[] args) {
        String secretKey = "c84dab82-12dc9c5f-47d4d371-b17df";
        String payload = "GET\n" +
                "api.huobi.pro:443\n" +
                "/ws/v1\n" +
                "AccessKeyId=6ab18816-7510b23c-70b60749-71c31&SignatureMethod=HmacSHA256&SignatureVersion=2&Timestamp=2018-09-27T08%3A30%3A30";
        String sign = HmacSHA256Signer.sign(payload, secretKey);


        String signatureMethodValue = "HmacSHA256";
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(signatureMethodValue);
            SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), signatureMethodValue);
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        }
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

        //获取签名，并进行Base64编码
        String actualSign = Base64.getEncoder().encodeToString(hash);


        HashMap<String, String> map = new HashMap<>();
        String sign1 = new HuobiSigner("6ab18816-7510b23c-70b60749-71c31", secretKey).sign("GET", "/ws/v1", map, "2018-09-27T08:30:30");

        if (sign.equals(sign1)) {
            System.out.println("asdfa");
        }


        if (actualSign.equals(sign)) {
            System.out.println("Sss");
        }
        System.out.println(sign);
    }


}
