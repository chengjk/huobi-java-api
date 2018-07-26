package com.huobi.api.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/21 3:08 PM
 */
@Getter
@AllArgsConstructor
public enum AccountState {
    WORKING("working"),
    LOCK("lock");
    String code;
}
