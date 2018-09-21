package com.huobi.api.client.domain.reqs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * created by jacky. 2018/9/21 3:11 PM
 */
@Getter
@Setter
@AllArgsConstructor
public class BatchCancelRequest {
    @JsonProperty("order-ids")
    private Set<String> orderIds;
}
