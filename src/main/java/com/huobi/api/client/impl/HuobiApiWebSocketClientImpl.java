package com.huobi.api.client.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.HuobiApiWebSocketClient;
import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * created by jacky. 2018/7/24 4:00 PM
 */
@Slf4j
public class HuobiApiWebSocketClientImpl implements HuobiApiWebSocketClient {

    private String apiKey;
    private String secretKey;
    private OkHttpClient client;

    public HuobiApiWebSocketClientImpl(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;

        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        this.client = new OkHttpClient.Builder().dispatcher(d).build();
    }


    @Override
    public Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback) {
        KlineEvent event = new KlineEvent();
        event.setSymbol(symbol);
        event.setPeriod(period);
        Closeable closeable = createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<KlineEventResp>(callback, KlineEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onKlineTick(symbol, period, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable requestKline(String symbol, Resolution period, long from, long to, ApiCallback<KlineEventResp> callback) {
        KlineEvent event = new KlineEvent();
        event.setSymbol(symbol);
        event.setPeriod(period);
        event.setFrom(from);
        event.setTo(to);
        Closeable closeable = createNewWebSocket(event.toRequest(), new HuobiApiWebSocketListener<KlineEventResp>(callback, KlineEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                requestKline(symbol, period, from, to, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable onDepthTick(String symbol, MergeLevel level, ApiCallback<DepthEventResp> callback) {
        DepthEvent event = new DepthEvent();
        event.setSymbol(symbol);
        event.setLevel(level);
        Closeable closeable = createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<DepthEventResp>(callback, DepthEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code,reason, event.toUnSub());
                onDepthTick(symbol, level, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable requestDepth(String symbol, MergeLevel level, long from, long to, ApiCallback<DepthEventResp> callback) {
        DepthEvent event = new DepthEvent();
        event.setSymbol(symbol);
        event.setLevel(level);
        event.setFrom(from);
        event.setTo(to);
        Closeable closeable = createNewWebSocket(event.toRequest(), new HuobiApiWebSocketListener<DepthEventResp>(callback, DepthEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code,reason, event.toUnSub());
                requestDepth(symbol, level, from, to, callback);
            }
        });
        return closeable;
    }


    @Override
    public Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback) {
        TradeDetailEvent event = new TradeDetailEvent();
        event.setSymbol(symbol);
        Closeable closeable = createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<TradeDetailResp>(callback, TradeDetailResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onTradeDetailTick(symbol, callback);
            }
        });
        return closeable;
    }


    @Override
    public Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback) {
        MarketDetailEvent event = new MarketDetailEvent();
        event.setSymbol(symbol);
        Closeable closeable = createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<MarketDetailResp>(callback, MarketDetailResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onMarketDetailTick(symbol, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable onOrderTick(String symbol, ApiCallback<OrderEventResp> callback) {
        OrderEvent event = new OrderEvent(symbol);
        event.setClientId("dzc_order_"+System.currentTimeMillis());
        Closeable closeable = newAuthWebSocket1(event, new HuobiApiWebSocketListener<OrderEventResp>(callback, OrderEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onOrderTick(symbol, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable onAccountTick(ApiCallback<AccountEventResp> callback) {
        AccountEvent event = new AccountEvent();
        event.setClientId("dzc_account_" + System.currentTimeMillis());
        return newAuthWebSocket1(event, new HuobiApiWebSocketListener<AccountEventResp>(callback, AccountEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onAccountTick(callback);
            }
        });
    }


    //todo for test.  huobi not support okhttp ws client ops!
    public Closeable onAccountTickOkhttp(ApiCallback<AccountEventResp> callback) {
        AccountEvent event = new AccountEvent();
        event.setClientId("dzc_account_" + System.currentTimeMillis());
        Closeable closeable = newAuthWebSocket(event.toAuth(apiKey, secretKey), new HuobiApiWebSocketListener<AccountEventResp>((webSocket, response) -> {
            if ("auth".equals(response.getOp())) {
                if ("0".equals(response.getErrCode())) {
                    webSocket.send(event.toSubscribe());
                }
            } else if ("notify".equals(response.getOp())) {
                callback.onResponse(webSocket, response);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                try {
                    log.error(mapper.writeValueAsString(response));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, AccountEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason,String unSub) {
                super.reconnect(webSocket, code, reason,event.toUnSub());
                onAccountTick(callback);
            }
        });
        return closeable;
    }

    private Closeable createNewWebSocket(String topic, HuobiApiWebSocketListener<?> listener) {
        String streamingUrl = HuobiConfig.WS_API_URL;
        return newWebSocket(streamingUrl, topic, listener);
    }

    private Closeable newAuthWebSocket(String topic, HuobiApiWebSocketListener<?> listener) {
        String streamingUrl = HuobiConfig.WS_API_URL + "/v1";
        return newWebSocket(streamingUrl, topic, listener);
    }

    private Closeable newAuthWebSocket1(WsEvent event, HuobiApiWebSocketListener<?> listener) {
        String streamingUrl = HuobiConfig.WS_API_URL + "/v1";
        try {
            URI uri = new URI(streamingUrl);
            HuobiApiAuthWebSocketClient client = new HuobiApiAuthWebSocketClient(uri, apiKey, secretKey);
            client.setTopic(event.toSubscribe());
            client.setClientId(event.getClientId());
            client.setListener(listener);
            client.connect();
            Closeable closeable = () -> {
                listener.onClosing(null, 1000, null); //  client.getConnection() 不兼容 okhttp.
                client.unSub();
                client.close();
                listener.onClosed(null, 1000, null);
            };
            listener.onConnect(null, closeable);
            return closeable;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Closeable newWebSocket(String url, String topic, HuobiApiWebSocketListener<?> listener) {
        Request request = new Request.Builder().url(url).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(topic);
        Closeable closeable = () -> {
            final int code = 4999;
            listener.onClosing(webSocket, code, "manual close.");
            webSocket.close(code, "manual close.");
            listener.onClosed(webSocket, code, "manual close.");
        };
        listener.onConnect(webSocket, closeable);
        return closeable;
    }
}
