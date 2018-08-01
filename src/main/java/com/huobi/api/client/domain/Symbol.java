package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:59 PM
 */
@Setter
@Getter
public class Symbol {
    private String symbol;
    @JsonProperty("base-currency")
    private String baseCurrency;
    @JsonProperty("quote-currency")
    private String quoteCurrency;
    @JsonProperty("price-precision")
    private String pricePrecision;
    @JsonProperty("amount-precision")
    private String amountPrecision;
    @JsonProperty("symbol-partition")
    private String symbolPartition;
}
