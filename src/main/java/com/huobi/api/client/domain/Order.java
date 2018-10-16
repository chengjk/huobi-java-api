package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.OrderSource;
import com.huobi.api.client.domain.enums.OrderState;
import com.huobi.api.client.domain.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/21 3:20 PM
 *
 */
@Getter
@Setter
public class Order {
    private String id;
    private String symbol;
    @JsonProperty(value = "account-id")
    private String accountId;
    private BigDecimal amount;
    private BigDecimal price;
    @JsonProperty(value = "created-at")
    private Long createdAt;
    private OrderType type;

    @JsonProperty(value = "filled-amount")
    private BigDecimal filledAmount;
    @JsonProperty(value = "filled-cash-amount") //未扣除fee
    private BigDecimal filledCashAmount;
    @JsonProperty(value = "filled-fees")
    private BigDecimal filledFees;

    @JsonProperty(value = "finished-at")
    private String finishedAt;
    @JsonProperty(value = "user-id")
    private String userId;
    private OrderSource source;
    private OrderState state;
    @JsonProperty(value = "canceled-at")
    private Long canceledAt;
    private String exchange;
    private String batch;

    //region deprecate
    //orders 接口返回的是 field-xxx, openOrder 接口返回的是 filled-xxx. 为了兼容，这两批字段内容保持同步。
    @JsonProperty(value = "field-amount")
    public void setFieldAmount(BigDecimal fieldAmount) {
        this.filledAmount = fieldAmount;
    }
    @JsonProperty(value = "field-cash-amount")
    public void setFieldCashAmount(BigDecimal fieldCashAmount) {
        this.filledCashAmount = fieldCashAmount;
    }
    @JsonProperty(value = "field-fees")
    public void setFieldFees(BigDecimal fieldFees) {
        this.filledFees = fieldFees;
    }
    //endregion
}
