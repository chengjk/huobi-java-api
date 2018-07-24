package com.huobi.api.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/23 8:26 PM
 */
@Getter
@AllArgsConstructor
public enum Resolution {
    M1("1min"),
    M5("5min"),
    M15("15min"),
    M30("30min"),
    M60("60min"),
    D1("1day"),
    MN1("1mon"),
    W1("1week"),
    Y1("1year");
    String name;

}
