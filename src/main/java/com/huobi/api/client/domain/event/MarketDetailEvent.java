package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/27 5:38 PM
 */
@Getter
@Setter
public class MarketDetailEvent extends WsBaseEvent {
    private String symbol;

    @Override
    public String toSubscribe() {
        String sub = "{" +
                "            \"sub\": \"%s\"," +
                "                \"id\":\"MarketDetail_%s\"" +
                "        }";
        return String.format(sub, getTopic(), symbol.toLowerCase());
    }

    @Override
    public String getTopic() {
        return String.format("market.%s.detail", symbol.toLowerCase());
    }
}
