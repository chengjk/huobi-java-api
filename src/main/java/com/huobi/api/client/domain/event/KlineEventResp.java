package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.domain.enums.Resolution;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/7/24 8:26 PM
 */
@Getter
@Setter
public class KlineEventResp extends WsNotify {
    private Candle tick;
    private List<Candle> data;
    private String symbol;
    private Resolution resolution;

    @Override
    public void setCh(String ch) {
        //market.ethbtc.kline.1min
        super.setCh(ch);
        String[] split = ch.split("\\.");
        symbol = split[1];
        resolution = Resolution.get(split[3]);
    }
}
