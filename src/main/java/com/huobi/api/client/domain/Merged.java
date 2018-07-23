package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

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
    private double open;
    private double close;
    private double low;
    private double high;
    private double amount;
    private double vol;
    private int count;
    private String symbol;
    /**
     *[买1价,买1量],
     */
    private TickerPrice bid;
    /**
     * [卖1价,卖1量]
     */
    private TickerPrice ask;

}
