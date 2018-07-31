package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.enums.OrderState;
import com.huobi.api.client.domain.enums.Resolution;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import sun.tools.java.ClassPath;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * created by jacky. 2018/7/21 10:48 AM
 */
@Slf4j
public class HuobiApiAsyncClientImplTest {


    private String apiKey = "a";
    private String apiSecret = "s";
    private HuobiApiRestClient client;

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        client = new HuobiApiRestClientImpl(apiKey, apiSecret);
    }

    @Test
    public void timestamp() {
        long timestamp = client.timestamp();
        assert timestamp > 0;
    }

    @Test
    public void tickers() {
        Set<Candle> tickers = client.tickers();
        assert tickers.size() > 0;
    }

    @Test
    public void kline() {
        Set<Candle> btcusdt = client.kline("btcusdt", Resolution.M1, 150);
        assert btcusdt.size() > 0;
    }

    @Test
    public void trade() {
        Set<Trade> trades = client.trade("btcusdt");
        assert trades != null;


    }

    @Test
    public void orders() {
        Set<Order> orders = client.orders("btcusdt", null, null, null, Arrays.asList(OrderState.values()), null, null, null);
        assert orders != null;
    }

    @Test
    public void getAccounts() {
        Set<Account> accounts = client.accounts();
        assert accounts != null;
        //2265332
    }

    @Test
    public void getBalance() {
        Account balance = client.balance("2265332");
        assert balance != null;
    }

    @Test
    public void openOrders() {
        Set<Order> openOrders = client.openOrders("2265332", "btcusdt", null, 100);
        assert openOrders != null;
    }


    @Test
    public void matchResults() {
        Set<MatchResult> matchResults = client.matchResults("btcusdt", null, null, null, null, null, null);
        assert matchResults != null;
    }


}