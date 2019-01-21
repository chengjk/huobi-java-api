package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Depth;
import com.huobi.api.client.domain.enums.MergeLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 8:43 PM
 */
@Getter
@Setter
public class DepthEventResp extends WsNotify {
    private Depth tick;
    private String symbol;
    private MergeLevel level;



    @Override
    public void setCh(String ch) {
        //market.btcusdt.depth.step0
        super.setCh(ch);
        String[] split = ch.split("\\.");
        symbol = split[1];
        level = MergeLevel.get(split[3]);
    }
}
