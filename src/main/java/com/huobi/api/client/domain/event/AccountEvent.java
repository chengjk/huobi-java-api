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
    /**
     * 选填,订阅账户balance类型。
     * 0 代表可用，即type=trade的balance;
     * 1 代表 total，即该账户的总余额，包括type=trade 和type=frozen的余额的和。
     * 当mode缺省时，默认值为0.
     * 建议：如需同时订阅可用和总余额，需要为 0 和 1 各开启一条 websocket 连接，如果使用同一条连接，后订阅的 topic 会覆盖前一个 topic
     */
    private Integer model = 0;

    @Override
    public String toSubscribe() {
        String format = "{\n" +
                "  \"op\": \"sub\",\n" +
                "  \"cid\": \"%s\",\n" +
                "  \"model\": \"%s\",\n" +
                "  \"topic\": \"%s\"\n" +
                "}";

        return String.format(format, clientId,model, topic);
    }


    public String toAuth() {
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
