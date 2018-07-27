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
                "  \"sub\": \"market.%s.trade.detail\",\n" +
                "  \"id\": \"%s\"\n" +
                "}";

        return String.format(sub, symbol.toLowerCase(), "TradeDetail_" + symbol.toLowerCase());
    }
}
