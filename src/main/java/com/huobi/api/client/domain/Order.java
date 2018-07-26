package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 3:20 PM
 */
@Getter
@Setter
public class Order {
    private String id;
    private String symbol;
    @JsonProperty(value = "account-id")
    private String accountId;
    private String amount;
    private String price;
    @JsonProperty(value = "created-at")
    private String createdAt;
    private String type;

    //region deprecate
    @JsonProperty(value = "field-amount")
    private String fieldAmount;
    @JsonProperty(value = "field-cash-amount")
    private String fieldCashAmount;
    @JsonProperty(value = "field-fees")
    private String fieldFees;
    //endregion

    @JsonProperty(value = "filled-amount")
    private String filledAmount;
    @JsonProperty(value = "filled-cash-amount")
    private String filledCashAmount;
    @JsonProperty(value = "filled-fees")
    private String filledFees;

    @JsonProperty(value = "finished-at")
    private String finishedAt;
    @JsonProperty(value = "user-id")
    private String userId;
    private String source;
    private String state;
    @JsonProperty(value = "canceled-at")
    private String canceledAt;
    private String exchange;
    private String batch;
}
