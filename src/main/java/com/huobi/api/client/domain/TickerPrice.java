package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:17 PM
 */
@Getter
@Setter
public class TickerPrice {
   private String symbol;
   private double price;
   private double vol;
}
