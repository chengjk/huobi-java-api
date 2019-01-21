package com.huobi.api.client.impl;

import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.domain.Depth;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
@Slf4j
public class HuobiApiWebSocketClientImplTest {
    Closeable stream;
    private String apiKey = "a";
    private String apiSecret = "s";
    private HuobiApiWebSocketClientImpl ws;
    private List<String> symbols = Arrays.asList(
            "ethbtc",
            "ltcbtc",
            "bchbtc",
            "xmrbtc",
            "qtumbtc",
            "xrpbtc",

            "xmreth",
            "qtumeth",

            "btcusdt",
            "ethusdt",
            "bchusdt",
            "qtumusdt",
            "xrpusdt"
    );

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        HuobiConfig.ReconnectOnFailure = true;
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        ws = new HuobiApiWebSocketClientImpl(apiKey, apiSecret);
    }


    @Test
    public void onKlineTick() {
        stream = ws.onKlineTick("ETHBTC", Resolution.M1, new ApiCallback<KlineEventResp>() {
            @Override
            public void onResponse(WebSocket ws, KlineEventResp data) {
                log.info(data.getTick().getClose().toPlainString());
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }

    @Test
    public void onKlineTickBatch() {
        stream = ws.onKlineTick(symbols,
                Arrays.asList(Resolution.values()),
                new ApiCallback<KlineEventResp>() {
                    @Override
                    public void onResponse(WebSocket ws, KlineEventResp data) {
                        log.info(data.getTick().getClose().toPlainString());
                    }

                    @Override
                    public void onConnect(WebSocket ws, Closeable closeable) {
                        stream = closeable;
                        log.info("onConnect :" + closeable.hashCode());
                    }
                });
        onDepthTickBatch();
    }

    @Test
    public void requestKline() {
        String symbol = "BTCUSDT";
        Resolution period = Resolution.M1;
        int step = 1;
        final long[] from = {1541126519};

        final long[] to = {from[0] + step * 60};
        stream = ws.requestKline(symbol, period, from[0], to[0], new ApiCallback<KlineEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket, KlineEventResp data) {
                data.getData().forEach(f -> log.info(f.getId() + ":" + f.getClose()));
                from[0] = to[0];
                to[0] = from[0] + step * 60; //
                KlineEvent event = new KlineEvent();
                event.setSymbol(symbol);
                event.setPeriod(period);
                event.setFrom(from[0]);
                event.setTo(to[0]);
                webSocket.send(event.toRequest());
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }

    @Test
    public void onDepthTick() {
        try {
            stream = ws.onDepthTick("BTCUSDT", MergeLevel.STEP0, new ApiCallback<DepthEventResp>() {
                @Override
                public void onResponse(WebSocket ws, DepthEventResp data) {
                    Depth tick = data.getTick();
                    if (tick != null) {
                        log.info("tick:" + tick.getId());
                    }
                }

                @Override
                public void onConnect(WebSocket ws, Closeable closeable) {
                    stream = closeable;
                    log.info("onConnect: " + stream.hashCode());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onDepthTickBatch() {
        ws.onDepthTick(symbols,
                Arrays.asList(MergeLevel.values()),
                new ApiCallback<DepthEventResp>() {
                    @Override
                    public void onResponse(WebSocket ws, DepthEventResp data) {
                        Depth tick = data.getTick();
                        if (tick != null) {
                            log.info(data.getSymbol() + "_" + data.getLevel() + "_tick:" + tick.getTs());
                        }
                    }

                    @Override
                    public void onConnect(WebSocket ws, Closeable closeable) {
                        stream = closeable;
                        log.info("onConnect: " + stream.hashCode());
                    }
                });
    }


    @Test
    public void requestDepth() {
        String symbol = "BTCUSDT";
        MergeLevel level = MergeLevel.STEP0;
        int step = 1;
        final long[] from = {1540697904};
        final long[] to = {from[0] + step * 60};
        stream = ws.requestDepth(symbol, level, from[0], to[0], new ApiCallback<DepthEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket, DepthEventResp data) {
                Depth tick = data.getTick();
                if (tick != null) {
                    from[0] = to[0];
                    to[0] = from[0] + step * 60;
                    DepthEvent event = new DepthEvent();
                    event.setSymbol(symbol);
                    event.setLevel(level);
                    event.setFrom(from[0]);
                    event.setTo(to[0]);
                    webSocket.send(event.toRequest());
                }
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }

    @Test
    public void onTradeDetailTick() {
        stream = ws.onTradeDetailTick("btcusdt", new ApiCallback<TradeDetailResp>() {
            @Override
            public void onResponse(WebSocket webSocket, TradeDetailResp response) {
                log.info(response.getTs() + "");
            }

            @Override
            public void onExpired(WebSocket webSocket, int code, String reason) {
                log.info("ws expired callback");
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }


    @Test
    public void onTradeDetailTickBatch() {
        stream = ws.onTradeDetailTick(symbols, new ApiCallback<TradeDetailResp>() {
            @Override
            public void onResponse(WebSocket webSocket, TradeDetailResp response) {
                log.info(response.getTs() + "");
            }

            @Override
            public void onExpired(WebSocket webSocket, int code, String reason) {
                log.info("ws expired callback");
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }

    @Test
    public void onMarketDetailTick() {
        stream = ws.onMarketDetailTick("btcusdt", new ApiCallback<MarketDetailResp>() {
            @Override
            public void onResponse(WebSocket ws, MarketDetailResp data) {
                Candle tick = data.getTick();
                String rise = tick.getClose().subtract(tick.getOpen()).divide(tick.getOpen(), 6, BigDecimal.ROUND_DOWN).toPlainString();
                log.info(String.format("rise:%s;high:%s;low:%s;vol:%s---open:%s;close:%s",
                        rise, tick.getHigh().toPlainString(), tick.getLow().toPlainString(), tick.getAmount().toPlainString(),
                        tick.getOpen().toPlainString(), tick.getClose().toPlainString()));
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }


    @Test
    public void onMarketDetailTickBatch() {
        stream = ws.onMarketDetailTick(symbols, new ApiCallback<MarketDetailResp>() {
            @Override
            public void onResponse(WebSocket ws, MarketDetailResp data) {
                Candle tick = data.getTick();
                String rise = tick.getClose().subtract(tick.getOpen()).divide(tick.getOpen(), 6, BigDecimal.ROUND_DOWN).toPlainString();
                log.info(String.format("rise:%s;high:%s;low:%s;vol:%s---open:%s;close:%s",
                        rise, tick.getHigh().toPlainString(), tick.getLow().toPlainString(), tick.getAmount().toPlainString(),
                        tick.getOpen().toPlainString(), tick.getClose().toPlainString()));
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }

    @Test
    public void onOrderTick() {
        stream = ws.onOrderTick("ethbtc", new ApiCallback<OrderEventResp>() {
            @Override
            public void onResponse(WebSocket webSocket, OrderEventResp response) {
                log.info(response.getCid());
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });
    }


    @Test
    public void onAccountTick() {
        stream = ws.onAccountTick(new ApiCallback<AccountEventResp>() {
            @Override
            public void onResponse(WebSocket ws, AccountEventResp data) {
                log.info(data.getCid());
            }

            @Override
            public void onConnect(WebSocket ws, Closeable closeable) {
                stream = closeable;
                log.info("onConnect :" + closeable.hashCode());
            }
        });

    }

    @After
    public void after() throws InterruptedException, IOException {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000L);
//            log.info("Stream: " + stream.hashCode());
        }
        stream.close();
    }
}
