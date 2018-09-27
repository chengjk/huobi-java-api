package com.huobi.api.client.domain.event;

/**
 * created by jacky. 2018/9/27 3:29 PM
 */
public interface WsAuthEvent extends WsEvent {

    default String toAuth(){
        String format = "{" +
                "\"SignatureVersion\":\"%s\"," +
                "\"op\":\"auth\"," +
                "\"AccessKeyId\":\"%s\"," +
                "\"Signature\":\"%s\"," +
                "\"SignatureMethod\":\"%s\"," +
                "\"Timestamp\":\"%s\"," +
                "\"cid\":\"%s\"" +
                "}";
        return format;
    }
}
