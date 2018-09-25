package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.AssetType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/23 3:28 PM
 */
@Getter
@Setter
public class Asset {
    @JsonProperty("account-id")
    private Long accountId;
    private String currency;
    private AssetType type;
    private BigDecimal balance;
}
