package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * created by jacky. 2018/7/21 2:24 PM
 * （合并深度0-5）；step0时，不合并深度
 */

@AllArgsConstructor
public enum MergeLevel {
    STEP0("step0"),
    STEP1("step1"),
    STEP2("step2"),
    STEP3("step3"),
    STEP4("step4"),
    STEP5("step5");
    private String code;

    public static MergeLevel get(String code) {

        return Arrays.stream(MergeLevel.values())
                .filter(f -> f.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown code"));
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
