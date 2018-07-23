package com.huobi.api.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/21 3:14 PM
 */
@Getter
@AllArgsConstructor
public enum OrderState {
    SUBMITTED("submitted"),
    PARTIAL_FILLED("partial-filled"),
    PARTIAL_CANCELED("partial-canceled"),
    CANCELED("canceled");
    private String code;
}
