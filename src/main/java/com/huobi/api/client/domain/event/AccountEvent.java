package com.huobi.api.client.domain.event;

import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.security.HuobiSigner;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * created by jacky. 2018/9/25 5:17 PM
 */
@Getter
@Setter
public class AccountEvent implements  WsEvent {

    private String clientId;
    @Override
    public String toSubscribe() {
        String format = "{\n" +
                "  \"op\": \"sub\",\n" +
                "  \"cid\": \"%s\",\n" +
                "  \"topic\": \"accounts\"\n" +
                "}";


        return String.format(format, clientId);
    }


    public String toAuth(String apiKey, String secretKey){
        String authFormat = "{" +
                "\"SignatureVersion\":\"%s\"," +
                "\"op\":\"auth\"," +
                "\"AccessKeyId\":\"%s\"," +
                "\"Signature\":\"%s\"," +
                "\"SignatureMethod\":\"%s\"," +
                "\"Timestamp\":\"%s\"," +
                "\"cid\":\"%s\"" +
                "}";

        HuobiSigner signer = new HuobiSigner(apiKey, secretKey);
        Map<String, String> param = new HashMap<>();
        String now = signer.gmtNow();
        String signature = signer.sign("GET", "/ws/v1", param, now);
        return String.format(authFormat, HuobiConsts.SIGNATURE_VERSION, apiKey, signature, HuobiConsts.SIGNATURE_METHOD, now, clientId);
    }
}
