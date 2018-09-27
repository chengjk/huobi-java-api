package com.huobi.api.client.domain.resp;

import okhttp3.WebSocket;

/**
 * created by jacky. 2018/7/24 7:44 PM
 */
public interface ApiCallback<T> {

    /**
     * Called whenever a response comes back
     * @param webSocket
     * @param response
     */
    void onResponse(WebSocket webSocket,T response);

    /**
     * Called whenever a error occur
     *
     * @param throwable
     */
    default void onFailure(Throwable throwable) {
    }

    /**
     * call when expired
     *
     * @param webSocket
     */
    default void onExpired(WebSocket webSocket,int code, String reason) {
    }

    /**
     * refer to onResponse
     *
     * call when receive message.
     *
     * @param webSocket
     */
    @Deprecated
    default void onMessage(WebSocket webSocket, String text) {
    }
}
