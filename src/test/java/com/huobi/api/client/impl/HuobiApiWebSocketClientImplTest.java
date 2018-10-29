package com.huobi.api.client.impl;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;
import okhttp3.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
public class HuobiApiWebSocketClientImplTest {
    Closeable stream;
    private String apiKey = "a";
    private String apiSecret = "s";
    private HuobiApiWebSocketClientImpl ws;

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        ws = new HuobiApiWebSocketClientImpl(apiKey,apiSecret);
    }



    @Test
    public void onKlineTick() {
        stream = ws.onKlineTick("BTCUSDT", Resolution.M1, (ws,data) -> {
            if (StringUtils.isNotEmpty(data.getSubbed())) {
                System.out.println(data.getSubbed());
            } else {
                System.out.println(data.getTick().getClose());
            }
        });
    }

    @Test
    public void requestKline() {
        String symbol = "BTCUSDT";
        Resolution period = Resolution.M1;
        int step = 5;
        final long[] from = {1509037320};

        final long[] to = {from[0] + step * 60};
        stream = ws.requestKline(symbol, period, from[0], to[0], new ApiCallback<KlineEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket,KlineEventResp data) {
                if (StringUtils.isNotEmpty(data.getSubbed())) {
                    System.out.println(data.getRep());
                } else {
                    data.getData().forEach(f -> System.out.println(f.getId() + ":" + f.getClose()));
                }
                from[0] = to[0];
                to[0] = from[0] + step * 60; //
                KlineEvent event = new KlineEvent();
                event.setSymbol(symbol);
                event.setPeriod(period);
                event.setFrom(from[0]);
                event.setTo(to[0]);
                webSocket.send(event.toRequest());
            }
        });
    }

    @Test
    public void onDepthTick() {
        try {
            stream = ws.onDepthTick("BTCUSDT", MergeLevel.STEP0, (ws,data) -> {
                System.out.println(data.getTick());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void requestDepth(){
        String symbol = "BTCUSDT";
        MergeLevel level = MergeLevel.STEP0;
        int step = 5;
        final long[] from = {1540697904};
        final long[] to = {from[0] + step * 60};
        stream = ws.requestDepth(symbol, level,from[0],to[0], new ApiCallback<DepthEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket,DepthEventResp data) {
                System.out.println(data.getTick());
                from[0] = to[0];
                to[0] = from[0] + step * 60;
                DepthEvent event = new DepthEvent();
                event.setSymbol(symbol);
                event.setLevel(level);
                event.setFrom(from[0]);
                event.setTo(to[0]);
                webSocket.send(event.toRequest());
            }
        });
    }

    @Test
    public void onTradeDetailTick() {
        stream = ws.onTradeDetailTick("BTCUSDT", new ApiCallback<TradeDetailResp>() {
            @Override
            public void onResponse(WebSocket webSocket,TradeDetailResp response) {
                System.out.println(response.getTs());
            }

            @Override
            public void onExpired(WebSocket webSocket,int code,String reason) {
                System.out.println("ws expired callback");
            }
        });
    }

    @Test
    public void onMarketDetailTick() {
        stream = ws.onMarketDetailTick("ltcusdt", (ws,data) -> {
            if (StringUtils.isEmpty(data.getSubbed())) {
                System.out.println(data.getTick().getAmount());
                System.out.println(data.getTick().getVol());
            }
        });
    }


    @Test
    public void onOrderTick() {
        stream = ws.onOrderTick("btcusdt", new ApiCallback<OrderEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket,OrderEventResp response) {
                System.out.println(response);
            }
        });
    }




    @Test
    public void onAccountTick(){
        stream = ws.onAccountTick((ws,data)->{
            System.out.println(data);
        });
    }
    @After
    public void after() throws InterruptedException, IOException {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000L);
        }
        stream.close();
    }
}