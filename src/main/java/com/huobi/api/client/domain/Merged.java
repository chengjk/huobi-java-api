package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by jacky. 2018/7/21 2:12 PM
 */
@Getter
@Setter
public class Merged {
    /**
     * K线id
     */
    private String id;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal amount;
    private BigDecimal vol;
    private BigDecimal count;
    private String symbol;
    /**
     *[买1价,买1量],
     */
    private List<BigDecimal> bid;
    /**
     * [卖1价,卖1量]
     */
    private List<BigDecimal> ask;

    private String version;

}
