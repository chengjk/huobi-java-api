package com.huobi.api.client.domain.resp;

import com.huobi.api.client.domain.Trade;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:40 PM
 */
@Getter
@Setter
public class TradeResp {
    private RespTick<Trade> tick;
}
