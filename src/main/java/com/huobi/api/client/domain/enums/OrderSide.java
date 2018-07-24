package com.huobi.api.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/21 2:35 PM
 */
@Getter
@AllArgsConstructor
public enum OrderSide {
    BUY("buy"), SELL("sell");
    private String code;
}
