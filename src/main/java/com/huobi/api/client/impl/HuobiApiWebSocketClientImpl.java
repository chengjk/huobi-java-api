package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiWebSocketClient;
import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.Closeable;

/**
 * created by jacky. 2018/7/24 4:00 PM
 */
public class HuobiApiWebSocketClientImpl implements HuobiApiWebSocketClient {

    private OkHttpClient client;


    public HuobiApiWebSocketClientImpl() {
        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        this.client = new OkHttpClient.Builder().dispatcher(d).build();
    }

    @Override
    public Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback) {
        KlineEvent event = new KlineEvent();
        event.setSymbol(symbol);
        event.setPeriod(period);
        return createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<KlineEventResp>(callback, KlineEventResp.class) {
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                if (code == 1003) {
                    onKlineTick(symbol, period, callback);
                }
            }
        });
    }

    @Override
    public Closeable requestKline(String symbol, Resolution period, long from, long to, ApiCallback<KlineEventResp> callback) {
        KlineEvent event = new KlineEvent();
        event.setSymbol(symbol);
        event.setPeriod(period);
        event.setFrom(from);
        event.setTo(to);
        return createNewWebSocket(event.toRequest(), new HuobiApiWebSocketListener<KlineEventResp>(callback, KlineEventResp.class) {
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                if (code == 1003) {
                    requestKline(symbol, period,from,to,callback);
                }
            }

        });
    }

    @Override
    public Closeable onDepthTick(String symbol, MergeLevel level, ApiCallback<DepthEventResp> callback) {
        DepthEvent event = new DepthEvent();
        event.setSymbol(symbol);
        event.setLevel(level);
        return createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<DepthEventResp>(callback, DepthEventResp.class) {
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                if (code == 1003) {
                    onDepthTick(symbol, level, callback);
                }
            }
        });
    }


    @Override
    public Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback) {
        TradeDetailEvent event = new TradeDetailEvent();
        event.setSymbol(symbol);
        return createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<TradeDetailResp>(callback, TradeDetailResp.class) {
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                if (code == 1003) {
                    onTradeDetailTick(symbol, callback);
                }
            }
        });
    }


    @Override
    public Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback) {
        MarketDetailEvent event = new MarketDetailEvent();
        event.setSymbol(symbol);
        return createNewWebSocket(event.toSubscribe(), new HuobiApiWebSocketListener<MarketDetailResp>(callback, MarketDetailResp.class){
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                if (code == 1003) {
                    onMarketDetailTick(symbol, callback);
                }
            }
        });
    }

    private Closeable createNewWebSocket(String topic, HuobiApiWebSocketListener<?> listener) {
        String streamingUrl = HuobiConsts.WS_API_URL;
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(topic);
        return () -> {
            final int code = 1000;
            listener.onClosing(webSocket, code, null);
            webSocket.close(code, null);
            listener.onClosed(webSocket, code, null);
        };
    }

}
