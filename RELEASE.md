# RELEASE LOG

## 20181115.1-1.0-SNAPSHOT

1. 模型字段类型从字符串按需改为数字类型（Long，Integer，BigDecimal）.
2. HuobiApiWebSocketListener 增加 manualClose 标记连接是不是手动关闭，若为手动关闭不重连。

## 20181105.1-1.0-SNAPSHOT

重构WebSocket 断线重连相关。

1. ws 除手动关闭以外，都重连。 手动关闭 `closing.close();`
2. apiCallback 增加 onConnect接口，返回断线重连后相关对象.
3. 增加断线重连相关配置
   1. AutoReconnect。
   是否开启断线重连，默认 true; WebSocketListener onClosing 时调用，若为true，除手动关闭以外都去重连。 
   2. ReconnectOnFailure
   失败时是否重连，默认false。初次订阅时或连上一段时间后都有可能触发，故此，调试时设置成false，保证参数正确运行时设为true。
   3. ReconnectOnExpired
   超时时是否重连，默认true。服务器ping多次未回应时触发。
   
4. WebSocketEvent 实现 unSub() 方法,用于断线重连时调用。

## 20181029.1-1.0-SNAPSHOT
fix error.  解决深度订阅失败、请求返回格式错误问题。

## 20181018.1-1.0-SNAPSHOT

原有返回列表的接口返回类型从 Set 改为List。
math-result 接口如果请求没有成交的订单时报错，客户内部消化该异常并返回空列表。

## 20181017.1-1.0-SNAPSHOT

Order 兼容 `field-xxx` 到 `filled-xxx`。 前者应该是huobi手误，历史原因不能改，这里做了兼容，使用该客户让统一使用 `filled-xxx`。

- field-amount
- field-cash-amount
- field-fees

## 1.0-SNAPSHOT

首个可用的版本，支持交易和行情。借贷api 没有经过测试。