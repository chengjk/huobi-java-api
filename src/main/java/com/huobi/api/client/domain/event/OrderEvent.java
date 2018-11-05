package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 3:59 PM
 */
@Getter
@Setter
public class OrderEvent implements WsEvent {
    private String symbol;
    private String clientId;

    public OrderEvent(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toSubscribe() {
        String format = "{" +
                "  \"op\": \"sub\"," +
                "  \"cid\": \"%s\"," +
                "  \"topic\": \"%s\"" +
                "}";

        return String.format(format, clientId, getTopic());
    }

    @Override
    public String getTopic() {
        return String.format("orders.%s", symbol.toLowerCase());
    }
}
