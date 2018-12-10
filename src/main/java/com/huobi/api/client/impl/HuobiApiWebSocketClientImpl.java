package com.huobi.api.client.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobi.api.client.HuobiApiWebSocketClient;
import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.enums.WsOp;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public Closeable onKlineTick(List<String> symbols, List<Resolution> resolutions, ApiCallback<KlineEventResp> callback) {
        List<WsEvent> events = new ArrayList<>();
        for (String symbol : symbols) {
            for (Resolution period : resolutions) {
                KlineEvent event = new KlineEvent();
                event.setSymbol(symbol);
                event.setPeriod(period);
                events.add(event);
            }
        }
        return createNewWebSocket(events, new HuobiApiWebSocketListener(callback, KlineEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                events.forEach(event -> super.reconnect(webSocket, code, reason, event.toUnSub()));
                onKlineTick(symbols, resolutions, callback);
            }
        });
    }

    @Override
    public Closeable onDepthTick(List<String> symbols, List<MergeLevel> levels, ApiCallback<DepthEventResp> callback) {
        List<WsEvent> events = new ArrayList<>();
        for (String symbol : symbols) {
            for (MergeLevel level : levels) {
                DepthEvent event = new DepthEvent();
                event.setSymbol(symbol);
                event.setLevel(level);
                events.add(event);
            }
        }
        return createNewWebSocket(events, new HuobiApiWebSocketListener(callback, DepthEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                events.forEach(event -> super.reconnect(webSocket, code, reason, event.toUnSub()));
                onDepthTick(symbols, levels, callback);
            }
        });
    }

    @Override
    public Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback) {
        return onKlineTick(Arrays.asList(symbol), Arrays.asList(period), callback);
    }

    @Override
    public Closeable requestKline(String symbol, Resolution period, long from, long to, ApiCallback<KlineEventResp> callback) {
        KlineEvent event = new KlineEvent();
        event.setSymbol(symbol);
        event.setPeriod(period);
        event.setFrom(from);
        event.setTo(to);
        event.setOp(WsOp.req);
        Closeable closeable = createNewWebSocket(Arrays.asList(event), new HuobiApiWebSocketListener(callback, KlineEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                super.reconnect(webSocket, code, reason, event.toUnSub());
                requestKline(symbol, period, from, to, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable onDepthTick(String symbol, MergeLevel level, ApiCallback<DepthEventResp> callback) {
        return onDepthTick(Arrays.asList(symbol), Arrays.asList(level), callback);
    }

    @Override
    public Closeable requestDepth(String symbol, MergeLevel level, long from, long to, ApiCallback<DepthEventResp> callback) {
        DepthEvent event = new DepthEvent();
        event.setSymbol(symbol);
        event.setLevel(level);
        event.setFrom(from);
        event.setTo(to);
        event.setOp(WsOp.req);
        Closeable closeable = createNewWebSocket(Arrays.asList(event), new HuobiApiWebSocketListener(callback, DepthEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                super.reconnect(webSocket, code, reason, event.toUnSub());
                requestDepth(symbol, level, from, to, callback);
            }
        });
        return closeable;
    }


    @Override
    public Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback) {
        return onTradeDetailTick(Arrays.asList(symbol), callback);
    }


    @Override
    public Closeable onTradeDetailTick(List<String> symbols, ApiCallback<TradeDetailResp> callback) {
        List<WsEvent> events = symbols.stream().map(s -> {
            TradeDetailEvent event = new TradeDetailEvent();
            event.setSymbol(s);
            return event;
        }).collect(Collectors.toList());

        return createNewWebSocket(events, new HuobiApiWebSocketListener(callback, TradeDetailResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                events.forEach(event -> super.reconnect(webSocket, code, reason, event.toUnSub()));
                onTradeDetailTick(symbols, callback);
            }
        });

    }

    @Override
    public Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback) {
        return onMarketDetailTick(Arrays.asList(symbol), callback);
    }

    @Override
    public Closeable onMarketDetailTick(List<String> symbols, ApiCallback<MarketDetailResp> callback) {
        List<WsEvent> events = symbols.stream().map(s -> {
            MarketDetailEvent event = new MarketDetailEvent();
            event.setSymbol(s);
            return event;
        }).collect(Collectors.toList());

        return createNewWebSocket(events, new HuobiApiWebSocketListener(callback, MarketDetailResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                events.forEach(event -> super.reconnect(webSocket, code, reason, event.toUnSub()));
                onMarketDetailTick(symbols, callback);
            }
        });
    }

    @Override
    public Closeable onOrderTick(String symbol, ApiCallback<OrderEventResp> callback) {
        OrderEvent event = new OrderEvent(symbol);
        event.setClientId("dzc_order_" + System.currentTimeMillis());
        Closeable closeable = newAuthWebSocket1(event, new HuobiApiWebSocketListener(callback, OrderEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                super.reconnect(webSocket, code, reason, event.toUnSub());
                onOrderTick(symbol, callback);
            }
        });
        return closeable;
    }

    @Override
    public Closeable onAccountTick(ApiCallback<AccountEventResp> callback) {
        AccountEvent event = new AccountEvent();
        event.setClientId("dzc_account_" + System.currentTimeMillis());
        return newAuthWebSocket1(event, new HuobiApiWebSocketListener(callback, AccountEventResp.class) {
            @Override
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                super.reconnect(webSocket, code, reason, event.toUnSub());
                onAccountTick(callback);
            }
        });
    }


    //todo for test.  huobi not support okhttp ws client ops!
    public Closeable onAccountTickOkhttp(ApiCallback callback) {
        AccountEvent event = new AccountEvent();
        event.setClientId("dzc_account_" + System.currentTimeMillis());
        event.setApiKey(apiKey);
        event.setSecretKey(secretKey);
        event.setOp(WsOp.auth);
        Closeable closeable = newAuthWebSocket(event, new HuobiApiWebSocketListener((ApiCallback<WsNotify>) (webSocket, response) -> {
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
            public void reconnect(WebSocket webSocket, int code, String reason, String unSub) {
                super.reconnect(webSocket, code, reason, event.toUnSub());
                onAccountTick(callback);
            }
        });
        return closeable;
    }

    private Closeable createNewWebSocket(List<WsEvent> events, HuobiApiWebSocketListener listener) {
        String streamingUrl = HuobiConfig.WS_API_URL;
        return newWebSocket(streamingUrl, events, listener);
    }

    private Closeable newAuthWebSocket(WsEvent event, HuobiApiWebSocketListener listener) {
        String streamingUrl = HuobiConfig.WS_API_URL + "/v1";
        return newWebSocket(streamingUrl, Arrays.asList(event), listener);
    }

    private Closeable newAuthWebSocket1(WsEvent event, HuobiApiWebSocketListener listener) {
        String streamingUrl = HuobiConfig.WS_API_URL + "/v1";
        try {
            URI uri = new URI(streamingUrl);
            HuobiApiAuthWebSocketClient client = new HuobiApiAuthWebSocketClient(uri, apiKey, secretKey);
            client.setTopic(event.toSubscribe());
            client.setClientId(event.getClientId());
            client.setListener(listener);
            client.connect();
            Closeable closeable = () -> {
                listener.setManualClose(true);
                int manualCloseCode = 4999;
                listener.onClosing(null, manualCloseCode, "manual close."); //  client.getConnection() 不兼容 okhttp.
                client.unSub();
                client.close();
                listener.onClosed(null, manualCloseCode, "manual close.");
            };
            listener.onConnect(null, closeable);
            return closeable;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Closeable newWebSocket(String url, List<WsEvent> events, HuobiApiWebSocketListener listener) {
        Request request = new Request.Builder().url(url).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        for (WsEvent event : events) {
            webSocket.send(event.toString());
        }
        Closeable closeable = () -> {
            listener.setManualClose(true);
            int manualCloseCode = 4999;
            listener.onClosing(webSocket, manualCloseCode, "manual close.");
            webSocket.close(manualCloseCode, "manual close.");
            listener.onClosed(webSocket, manualCloseCode, "manual close.");
        };
        listener.onConnect(webSocket, closeable);
        return closeable;
    }
}
