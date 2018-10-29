# RELEASE LOG


## 20181029.1-1.0-SNAPSHOT
fix error.  解决深度订阅失败、请求返回格式错误问题。

## 20181018.1-1.0-SNAPSHOT

原有返回列表的接口返回类型从 Set 改为List。
math-result 接口如果请求没有成交的订单时报错，客户内部小伙该异常并返回空列表。

## 20181017.1-1.0-SNAPSHOT

Order 兼容 `field-xxx` 到 `filled-xxx`。 前者应该是huobi手误，历史原因不能改，这里做了兼容，使用该客户让统一使用 `filled-xxx`。

- field-amount
- field-cash-amount
- field-fees

## 1.0-SNAPSHOT

首个可用的版本，支持交易和行情。借贷api 没有经过测试。