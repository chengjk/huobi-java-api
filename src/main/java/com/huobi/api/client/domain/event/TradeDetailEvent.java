package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/26 6:05 PM
 */
@Getter
@Setter
public class TradeDetailEvent implements WsEvent {
    private String symbol;

    @Override
    public String toSubscribe() {

        String sub = "{\n" +
                "  \"sub\": \"%s\",\n" +
                "  \"id\": \"%s\"\n" +
                "}";

        return String.format(sub, getTopic(), "TradeDetail_" + symbol.toLowerCase());
    }

    @Override
    public String getTopic() {
        return String.format("market.%s.trade.detail", symbol.toLowerCase());
    }
}
