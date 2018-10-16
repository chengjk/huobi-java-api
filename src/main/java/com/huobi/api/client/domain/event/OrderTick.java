package com.huobi.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.OrderRole;
import com.huobi.api.client.domain.enums.OrderSource;
import com.huobi.api.client.domain.enums.OrderState;
import com.huobi.api.client.domain.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 4:05 PM
 */
@Getter
@Setter
public class OrderTick {
    //流水号(不连续)
    @JsonProperty("seq-id")
    private Long seqId;
    //订单 id
    @JsonProperty("order-id")
    private Long orderId;
    //交易对
    @JsonProperty("symbol")
    private String symbol;
    //账户 id
    @JsonProperty("account-id")
    private Long accountId;
    //订单数量
    @JsonProperty("order-amount")
    private String orderAmount;
    //订单价格
    @JsonProperty("order-price")
    private String orderPrice;
    //订单创建时间
    @JsonProperty("created-at")
    private Long createdAt;
    //订单类型，请参考订单类型说明
    @JsonProperty("order-type")
    private OrderType orderType;
    //订单来源，请参考订单来源说明
    @JsonProperty("order-source")
    private OrderSource orderSource;
    //订单状态，请参考订单状态说明
    @JsonProperty("order-state")
    private OrderState orderState;
    //maker, taker
    @JsonProperty("role")
    private OrderRole role;
    //成交价格
    @JsonProperty("price")
    private String price;
    //单次成交数量
    @JsonProperty("filled-amount")
    private String filledAmount;
    //单次未成交数量
    @JsonProperty("unfilled-amount")
    private String unfilledAmount;
    //单次成交金额
    @JsonProperty("filled-cash-amount")
    private String filledCashAmount;
    //单次成交手续费（买入为币，卖出为钱）
    @JsonProperty("filled-fees")
    private String filledFees;

    @JsonProperty("user-id")
    private String userId;





}
