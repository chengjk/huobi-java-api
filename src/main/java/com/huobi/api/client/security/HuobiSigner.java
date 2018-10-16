package com.huobi.api.client.security;

import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.constant.HuobiConsts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;

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
                .append(HuobiConfig.API_HOST.toLowerCase()+":443").append('\n') // Host
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

}
