package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/20 8:52 PM
 */
@Getter
@Setter
public class Candle {
    private long id;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal amount;
    private BigDecimal vol;
    private BigDecimal count;
    private String symbol;
    private long ts;
    private long version;

}
