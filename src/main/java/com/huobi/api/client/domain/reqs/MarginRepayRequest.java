package com.huobi.api.client.domain.reqs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/21 11:50 AM
 */
@Getter
@Setter
public class MarginRepayRequest {
    @JsonProperty("order-id")
    private String orderId;
    private String amount;

    public MarginRepayRequest(String amount) {
        this.amount = amount;
    }
}
