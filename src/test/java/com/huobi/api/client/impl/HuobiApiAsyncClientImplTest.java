package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.domain.Kline;
import com.huobi.api.client.domain.Order;
import com.huobi.api.client.domain.OrderStatus;
import com.huobi.api.client.domain.Trade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Set;

/**
 * created by jacky. 2018/7/21 10:48 AM
 */
@Slf4j
public class HuobiApiAsyncClientImplTest {

    private HuobiApiRestClient client = new HuobiApiRestClientImpl("a", "s");

    @Test
    public void timestamp() {
        long timestamp = client.timestamp();
        assert timestamp > 0;
    }

    @Test
    public void tickers() {
        Set<Kline> tickers = client.tickers();
        assert tickers.size() > 0;
    }

    @Test
    public void kline() {
        Set<Kline> btcusdt = client.kline("btcusdt", "1min", 150);
        assert btcusdt.size() > 0;
    }

    @Test
    public void trade() {
        Set<Trade> trades = client.trade("btcusdt");
        assert trades != null;


    }
    @Test
    public void orders() {
        Set<Order> orders = client.orders("btcusdt",OrderStatus.SUBMITTED);
        assert orders != null;
    }
}