package com.huobi.api.client.domain.resp;

/**
 * created by jacky. 2018/7/24 7:44 PM
 */
@FunctionalInterface
public interface ApiCallback<T> {

    /**
     * Called whenever a response comes back
     *
     * @param response
     */
    void onResponse(T response);

    /**
     * Called whenever a error occur
     *
     * @param throwable
     */
    default void onFailure(Throwable throwable) {
    }

}
