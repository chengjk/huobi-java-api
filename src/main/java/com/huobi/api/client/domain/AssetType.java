package com.huobi.api.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/7/23 4:11 PM
 */
@Getter
@AllArgsConstructor
public enum AssetType {
    WORKING("working"),
    TRADE("trade"),
    FROZEN("frozen"),
    LOAN("loan"),
    INTEREST("interest"),
    TRANSFER_OUT_AVAILABLE("transfer_out_available"),
    LOAN_AVAILABLE("loan_available");
    private String name;

}
