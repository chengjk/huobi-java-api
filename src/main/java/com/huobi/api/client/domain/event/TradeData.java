package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeData {
    private String id;
    private long ts;
    private String amount;
    private String price;
    private String direction;
}
