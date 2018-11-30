package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/11/30 2:19 PM
 */
@Getter
@Setter
public class ContractPriceLimit {
    private String symbol;
    @JsonProperty("high_limit")
    private BigDecimal highLimit;
    @JsonProperty("low_limit")
    private BigDecimal lowLimit;
    @JsonProperty("contract_code")
    private String code;
    @JsonProperty("contract_type")
    private ContractType type;
}
