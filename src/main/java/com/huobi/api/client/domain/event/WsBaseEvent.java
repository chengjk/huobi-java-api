package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.WsOp;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/12/7 7:27 PM
 */
@Getter
@Setter
public abstract class WsBaseEvent implements WsEvent {
    private WsOp op = WsOp.sub;
    private String apiKey;
    private String secretKey;
    @Override
    public String toString(){
        switch (op) {
            case sub:
                return toSubscribe();
            case req:
                return toRequest();
            case unsub:
                return toUnSub();
            case auth:
                return toAuth();
            default:
                return toSubscribe();
        }
    }
}
