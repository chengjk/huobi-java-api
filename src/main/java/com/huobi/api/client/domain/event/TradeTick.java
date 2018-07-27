package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TradeTick {
    private String id;
    private long ts;
    private Set<TradeData> data;
}
