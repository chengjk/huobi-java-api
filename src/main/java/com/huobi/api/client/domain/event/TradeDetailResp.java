package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/26 6:08 PM
 */
@Getter
@Setter
public class TradeDetailResp extends WsNotify {
    private TradeTick tick;
    private String symbol;

    @Override
    public void setCh(String ch) {
        //market.btcusdt.trade.detail
        super.setCh(ch);
        String[] split = ch.split("\\.");
        symbol = split[1];
    }
}

