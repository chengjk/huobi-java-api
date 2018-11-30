package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/11/30 2:26 PM
 */
@Getter
@Setter
public class Interest {
    private String symbol;
    @JsonProperty("contract_type")
    private ContractType type;
    private BigDecimal volume;
    private BigDecimal amount;
    @JsonProperty("contract_code")
    private String code;
}
