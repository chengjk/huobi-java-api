package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/20 8:52 PM
 */
@Getter
@Setter
public class Candle {
    private long id;
    private double open;
    private double close;
    private double low;
    private double high;
    private double amount;
    private double vol;
    private int count;
    private String symbol;
    private long ts;
    private long version;

}
