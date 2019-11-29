package com.huobi.api.client.domain.reqs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/10 3:55 PM
 */
@Getter
@Setter
public class PlaceOrderRequest {
    @JsonProperty("account-id")
    private String accountId;
    private String amount;
    private String price;
    private String source;
    private String symbol;
    private String type;
    //用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
    @JsonProperty("client-order-id")
    private String clientOrderId;
    //止盈止损订单触发价格
    @JsonProperty("stop-price")
    private String stopPrice;
    //止盈止损订单触发价运算符 gte – greater than and equal (>=), lte – less than and equal (<=)
    private String operator;

}
