package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Candle;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/27 7:50 PM
 */
@Getter
@Setter
public class MarketDetailResp extends WsNotify {
    private Candle tick;
    private String symbol;

    @Override
    public void setCh(String ch) {
        //market.ethbtc.detail
        super.setCh(ch);
        String[] split = ch.split("\\.");
        symbol = split[1];
    }

}
