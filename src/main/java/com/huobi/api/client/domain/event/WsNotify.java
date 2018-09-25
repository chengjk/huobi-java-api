package com.huobi.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 4:56 PM
 */
@Getter
@Setter
public class WsNotify {

    private String id;
    private String ch;
    private String subbed;
    private String rep;



    private String op;
    private String topic;
    private String status;
    private String cid;
    @JsonProperty("err-code")
    private String errCode;
    @JsonProperty("err-msg")
    private String errMsg;
    private long ts;





}
