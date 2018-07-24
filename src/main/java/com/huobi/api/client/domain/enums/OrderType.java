package com.huobi.api.client.domain.enums;

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
    SELL_IOC("sell-ioc");
    String code;
}
