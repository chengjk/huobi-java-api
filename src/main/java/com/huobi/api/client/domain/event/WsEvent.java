package com.huobi.api.client.domain.event;

/**
 * created by jacky. 2018/7/24 5:48 PM
 */
public interface WsEvent {

    String getTopic();

    default String getClientId() {
        return System.currentTimeMillis() + "";
    }

    String toSubscribe();

    default String toRequest() {
        return null;
    }

    default String toAuth() {
        return null;
    }

    default String toUnSub() {
        return String.format("{\"op\":\"unsub\",\"topic\":\"%s\",\"cid\":\"%s\"}", getTopic(), getClientId());
    }

}
