package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Candle;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/27 7:50 PM
 */
@Getter
@Setter
public class MarketDetailResp {
    private String status;
    private String id;
    private String ch;
    private long ts;
    private String subbed;

    private Candle tick;

}
