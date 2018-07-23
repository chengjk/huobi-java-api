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
    private String account_id;
    private String amount;
    private String price;
    @JsonProperty(value = "created-at")
    private String created_at;
    private String type;

    //region deprecate
    @JsonProperty(value = "field-amount")
    private String field_amount;
    @JsonProperty(value = "field-cash-amount")
    private String field_cash_amount;
    @JsonProperty(value = "field-fees")
    private String field_fees;
    //endregion

    @JsonProperty(value = "filled-amount")
    private String filled_amount;
    @JsonProperty(value = "filled-cash-amount")
    private String filled_cash_amount;
    @JsonProperty(value = "filled-fees")
    private String filled_fees;

    @JsonProperty(value = "finished-at")
    private String finished_at;
    @JsonProperty(value = "user-id")
    private String user_id;
    private String source;
    private String state;
    @JsonProperty(value = "canceled-at")
    private String canceled_at;
    private String exchange;
    private String batch;
}
