package com.huobi.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 4:12 PM
 */
@Getter
@Setter
public class OrderEventResp {
    private String op;
    private String topic;
    private String status;
    private String cid;
    private OrderTick data;

    @JsonProperty("err-code")
    private String errCode;
    @JsonProperty("err-msg")
    private String errMsg;
    private long ts;
}
