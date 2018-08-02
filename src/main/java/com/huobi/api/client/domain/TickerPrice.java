package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/21 2:17 PM
 */
@Getter
@Setter
public class TickerPrice {
   private String symbol;
   private BigDecimal price;
   private BigDecimal vol;
}
