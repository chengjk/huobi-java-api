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
public class KlineEventResp extends WsNotify {
    private Candle tick;
    private List<Candle> data;
}
