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
public class AccountEvent extends WsBaseEvent {

    private String clientId;
    private String topic = "accounts";
    @Override
    public String toSubscribe() {
        String format = "{\n" +
                "  \"op\": \"sub\",\n" +
                "  \"cid\": \"%s\",\n" +
                "  \"topic\": \"%s\"\n" +
                "}";


        return String.format(format, clientId,topic);
    }


    public String toAuth(){
        String authFormat = "{" +
                "\"SignatureVersion\":\"%s\"," +
                "\"op\":\"auth\"," +
                "\"AccessKeyId\":\"%s\"," +
                "\"Signature\":\"%s\"," +
                "\"SignatureMethod\":\"%s\"," +
                "\"Timestamp\":\"%s\"," +
                "\"cid\":\"%s\"" +
                "}";

        HuobiSigner signer = new HuobiSigner(getApiKey(), getSecretKey());
        Map<String, String> param = new HashMap<>();
        String now = signer.gmtNow();
        String signature = signer.sign("GET", "/ws/v1", param, now);
        return String.format(authFormat, HuobiConsts.SIGNATURE_VERSION, getApiKey(), signature, HuobiConsts.SIGNATURE_METHOD, now, clientId);
    }
}
