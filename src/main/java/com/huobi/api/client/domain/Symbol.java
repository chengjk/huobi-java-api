package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    private Integer pricePrecision;
    @JsonProperty("amount-precision")
    private Integer amountPrecision;
    @JsonProperty("symbol-partition")
    private String symbolPartition;
    private String state;
    @JsonProperty("value-precision")
    private Integer valuePrecision;
    @JsonProperty("min-order-amt")
    private BigDecimal minOrderAmt;
    @JsonProperty("max-order-amt")
    private BigDecimal maxOrderAmt;
    //最小下单金额 （下单金额指当订单类型为限价单时，下单接口传入的(amount * price)。当订单类型为buy-market时，下单接口传的'amount'）
    @JsonProperty("min-order-value")
    private BigDecimal minOrderValue;
    @JsonProperty("leverage-ratio")
    private Integer leverageRatio;
    @JsonProperty("super-margin-leverage-ratio")
    private Integer superMarginLeverageRatio;
}
