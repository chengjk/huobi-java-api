package com.huobi.api.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * created by jacky. 2018/7/23 3:34 PM
 */
@AllArgsConstructor
@Getter
public enum OrderSource {
    API("api"), MARGIN_API("margin-api");
    private String code;

    public OrderSource get(String code) {
        return Arrays.stream(OrderSource.values())
                .filter(f -> f.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown code"));
    }
}
