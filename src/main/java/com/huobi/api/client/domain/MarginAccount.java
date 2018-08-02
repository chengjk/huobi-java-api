package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/23 4:08 PM
 */
@Getter
@Setter
public class MarginAccount  extends  Account{
    private String symbol;
    @JsonProperty("fl-price")
    private BigDecimal flPrice;
    @JsonProperty("fl-type")
    private String flType;
    @JsonProperty("risk-rate")
    private BigDecimal riskRate;
}
