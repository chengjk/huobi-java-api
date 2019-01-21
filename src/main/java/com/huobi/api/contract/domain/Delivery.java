package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2019/1/21 3:18 PM
 */
@Getter
@Setter
public class Delivery {
    @JsonProperty("delivery_price")
    private String deliveryPrice;
}
