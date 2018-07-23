package com.huobi.api.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 3:56 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuobiApiException extends Exception {
    private String errCode;
    private String errMsg;

    public HuobiApiException(String message) {
        super(message);
    }

    public HuobiApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public HuobiApiException(Throwable cause) {
        super(cause);
    }

    protected HuobiApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
