package com.huobi.api.client.impl;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.KlineEvent;
import com.huobi.api.client.domain.event.KlineEventResp;
import com.huobi.api.client.domain.event.TradeDetailResp;
import com.huobi.api.client.domain.resp.ApiCallback;
import okhttp3.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
public class HuobiApiWebSocketClientImplTest {
    private HuobiApiWebSocketClientImpl ws = new HuobiApiWebSocketClientImpl();
    Closeable stream;

    @Test
    public void onKlineTick() {
        stream = ws.onKlineTick("BTCUSDT", Resolution.M1, data -> {
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
            public void onResponse(KlineEventResp data) {
                if (StringUtils.isNotEmpty(data.getSubbed())) {
                    System.out.println(data.getRep());
                } else {
                    data.getData().forEach(f -> System.out.println(f.getId() + ":" + f.getClose()));
                }
            }
            @Override
            public void onMessage(WebSocket webSocket) {
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
        stream = ws.onDepthTick("BTCUSDT", MergeLevel.STEP0, data -> {
            System.out.println(data.getRep());
        });
    }


    @Test
    public void onTradeDetailTick() {
        stream = ws.onTradeDetailTick("BTCUSDT", new ApiCallback<TradeDetailResp>() {
            @Override
            public void onResponse(TradeDetailResp response) {
                System.out.println(response.getTs());
            }

            @Override
            public void onExpired(WebSocket webSocket) {
                System.out.println("ws expired callback");
            }
        });
    }

    @Test
    public void onMarketDetailTick() {
        stream = ws.onMarketDetailTick("ltcusdt", data -> {
            if (StringUtils.isEmpty(data.getSubbed())) {
                System.out.println(data.getTick().getAmount());
                System.out.println(data.getTick().getVol());
            }
        });
    }


    @After
    public void after() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000L);
        }
        stream.close();
    }
}