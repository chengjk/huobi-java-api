package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/21 3:36 PM
 */
@Getter
@AllArgsConstructor
public enum OrderType {
    BUY_MARKET("buy-market"),
    SELL_MARKET("sell-market"),
    BUY_LIMIT("buy-limit"),
    SELL_LIMIT("sell-limit"),
    BUY_IOC("buy-ioc"),
    SELL_IOC("sell-ioc"),

    /**
     * 当“下单价格”>=“市场最低卖出价”，订单提交后，系统将拒绝接受此订单；
     * 当“下单价格”<“市场最低卖出价”，提交成功后，此订单将被系统接受。
     */
    BUY_LIMIT_MAKER("buy-limit-maker"),
    /**
     * 当“下单价格”<=“市场最高买入价”，订单提交后，系统将拒绝接受此订单；
     * 当“下单价格”>“市场最高买入价”，提交成功后，此订单将被系统接受。
     */
    SELL_LIMIT_MAKER("sell-limit-maker");

    private String code;


    @JsonValue
    public String getCode() {
        return code;
    }
}
