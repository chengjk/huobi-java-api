package com.huobi.api.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/23 8:21 PM
 */
@Getter
@AllArgsConstructor
public enum AccountType {
    SPOT("spot");//现货账户
    String code;
}
