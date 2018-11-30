package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/11/30 2:16 PM
 */
@Getter
@Setter
public class ContractIndex {
    private String symbol;
    @JsonProperty("index_price")
    private BigDecimal price;
    @JsonProperty("index_ts")
    private Long indexTs;
}
