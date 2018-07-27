package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/27 5:38 PM
 */
@Getter
@Setter
public class MarketDetailEvent implements WsEvent {
    private String symbol;

    @Override
    public String toSubscribe() {
        String sub = "{\n" +
                "            \"sub\": \"market.%s.detail\",\n" +
                "                \"id\":\"MarketDetail_%s\"\n" +
                "        }";
        return String.format(sub, symbol.toLowerCase(), symbol.toLowerCase());
    }
}
