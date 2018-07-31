package com.huobi.api.client.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 11:16 AM
 */
@Getter
@Setter
public class RespBody<T> {
    private String status;
    private long ts;
    private String ch;
    private T data;
    private T tick;
    @JsonProperty("err-code")
    private String errCode;
    @JsonProperty("err-msg")
    private String errMsg;


    public String toErrorString() {
        String msg = "errCode:%s, errMsg:%s.";
        return String.format(msg, errCode, errMsg);
    }


}
