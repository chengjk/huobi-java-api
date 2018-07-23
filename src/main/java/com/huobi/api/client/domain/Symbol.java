package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:59 PM
 */
@Setter
@Getter
public class Symbol {
    private String baseCurrency;
    private String quoteCurrency;
    private String pricePrecision;
    private String amountPrecision;
    private String symbolPartition;
}
