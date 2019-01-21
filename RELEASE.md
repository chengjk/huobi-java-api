# RELEASE LOG

## 20190118.1-1.0-SNAPSHOT
1. 订阅account资产推送，增加订阅参数mode. 订阅返回的内容中不再推送交易子账户冻结余额的变化
2. `ApiCallback` 增加 `onPing` 方法。服务器ping的时候调用，用于监控链接的状态，如果长时间没有更新，说明连接已经断开。
3. WebSocket 的状态码都移动到常量中。
4. 更新retrofit 版本到2.5.0

## 20181208.1-1.0-SNAPSHOT
支持批量订阅 WebSocket，避免由订阅引起的 `too many request` 错误。相同业务类的订阅推荐使用批量订阅，例如全部symbol的所有resolution的kline都放到一个批次里，深度数据也是。
相应的WsResponse 里增加一些业务属性。

1. DepthEventResp 增加 symbol，merge level。
2. KlineEventResp 增加 symbol， resolution。
3. MarketDetailResp 增加 symbol。
4. TradeDetailResp 增加 symbol。

重构`WsEvent`相关。 重构`WebSocketListener`，`onResponse` 中不再需要判断是否为第一次返回。 
> 第一次返回 "subed:xxxxx",并不包含数据。

在onResponse 中区分业务属性做相应处理。

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
