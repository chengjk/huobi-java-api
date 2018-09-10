package com.huobi.api.client.domain.reqs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/10 3:55 PM
 */
@Getter
@Setter
public class PlaceOrderRequest {
    @JsonProperty("account-id")
    private String accountId;
    private String amount;
    private String price;
    private String source;
    private String symbol;
    private String type;
}
