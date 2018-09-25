package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

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
}
