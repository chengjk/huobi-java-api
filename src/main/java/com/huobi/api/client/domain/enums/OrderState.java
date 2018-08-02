package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * created by jacky. 2018/7/21 3:14 PM
 */
@AllArgsConstructor
public enum OrderState {
    SUBMITTED("submitted"),
    PARTIAL_FILLED("partial-filled"),
    FILLED("filled"),
    PARTIAL_CANCELED("partial-canceled"),
    CANCELED("canceled");
    private String code;

    @JsonValue
    public String getCode() {
        return code;
    }
}
