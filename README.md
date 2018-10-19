# huobi-java-api
huobi Restful api java client. 

[release log][./RELEASE.md]

## Configuration

### 环境切换
设置api host,用于在测试，正式和Mock环境切换 

```java
HuobiConfig.API_HOST="api.huobi.pro"
HuobiConfig.REST_API_UR="https://api.huobi.pro"
HuobiConfig.WS_API_URL="wss://api.huobi.pro:443/ws"

```
### How configure unit test

1. make dir  `src/test/resources/`, make dir as Test Resource Root
2. create file `config.properties`.
    
    ``` properties
    #sample
    #apiKey=e8d23f3b-92721ba8-7b804c0f-xxxx
    #apiSecret=bd14ad47-fe8f8ed9-bf3e32df-xxxx
    ```

## 需要鉴权的WebSocket 

这类 web socket目前不支持 `okhttp`所以引入了 `Java-WebSocket` ,逻辑在`HuobiApiAuthWebSocketClient` 中。 等huobi兼容后移除该依赖。 

