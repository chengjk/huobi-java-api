package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/11/30 11:39 AM
 */
@Getter
@Setter
public class ContractInfo {
    private String symbol;
    @JsonProperty("contract_code")
    private String code;
    @JsonProperty("contract_type")
    private ContractType type;
    @JsonProperty("contract_size")
    private Integer size;
    @JsonProperty("price_tick")
    private BigDecimal priceTick;
    @JsonProperty("delivery_date")
    private String deliveryDate;
    @JsonProperty("create_date")
    private String createDate;
    @JsonProperty("contract_status")
    private ContractStatus status;
}
