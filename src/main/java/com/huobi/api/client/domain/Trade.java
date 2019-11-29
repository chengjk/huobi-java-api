package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.OrderSide;
import lombok.Data;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/21 2:33 PM
 */
@Data
public class Trade {
    @JsonProperty("trade-id")
    private String tradeId;
    private String id;
    private BigDecimal price;
    private BigDecimal amount;
    private OrderSide direction;
    private long ts;
}
