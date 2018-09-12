package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Candle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/7/24 8:26 PM
 */
@Getter
@Setter
public class KlineEventResp {
    private String id;
    private String status;
    private long ts;
    private String ch;
    private Candle tick;
    private String subbed;


    //-----req---
    private String rep;
    private List<Candle> data;
}
