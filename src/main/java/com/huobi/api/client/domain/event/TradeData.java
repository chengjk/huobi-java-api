package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.OrderSide;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TradeData {
    private String id;
    private long ts;
    private BigDecimal amount;
    private BigDecimal price;
    private OrderSide direction;
}
