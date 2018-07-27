package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * created by jacky. 2018/7/23 3:34 PM
 */
@AllArgsConstructor
public enum OrderSource {
    API("api"), MARGIN_API("margin-api");
    private String code;


    @JsonValue
    public String getCode() {
        return code;
    }

    public OrderSource get(String code) {
        return Arrays.stream(OrderSource.values())
                .filter(f -> f.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown code"));
    }
}
