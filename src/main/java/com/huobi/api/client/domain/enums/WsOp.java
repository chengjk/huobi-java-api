package com.huobi.api.client.domain.enums;

/**
 * created by jacky. 2018/12/7 6:14 PM
 */
public enum WsOp {
    ping,//心跳发起(server)
    pong,//心跳应答
    auth,//鉴权
    sub,//订阅消息
    unsub,//取消订阅消息
    req,//请求数据
    notify,//推送订阅消息(server)
}
