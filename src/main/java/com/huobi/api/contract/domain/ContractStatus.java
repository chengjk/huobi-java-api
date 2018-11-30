package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * created by jacky. 2018/11/30 11:36 AM
 */
@AllArgsConstructor
public enum  ContractStatus {
    a(0),
    b(1),
    c(2),
    d(3),
    e(4),
    f(5),
    g(6),
    h(7),
    i(8),
    j(9);
    int code;

    @JsonValue
    public int getCode() {
        return code;
    }
}
