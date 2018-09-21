package com.huobi.api.client.domain.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/21 11:44 AM
 */
@Getter
@Setter
@AllArgsConstructor
public class TransferMarginRequest {
    private String symbol;
    private String currency;
    private String amount;
}
