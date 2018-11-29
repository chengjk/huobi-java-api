package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * created by jacky. 2018/11/19 2:52 PM
 */
@AllArgsConstructor
public enum QueryDirection {
    NEXT("next"),
    PREV("prev");
    private String name;
    @JsonValue
    public String getName() {
        return name;
    }

}
