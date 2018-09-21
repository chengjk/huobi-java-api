package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.enums.*;
import com.huobi.api.client.domain.resp.BatchCancelResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

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
        System.out.println("server:" + timestamp);
        long local = System.currentTimeMillis();
        System.out.println("local:" + local);
        System.out.println("delay:" + (local - timestamp));
        assert timestamp > 0;
    }

    @Test
    public void tickers() {
        Set<Candle> tickers = client.tickers();
        assert tickers.size() > 0;
    }

    @Test
    public void depth() {
        Depth depth = client.depth("btcusdt", MergeLevel.STEP0);
        assert depth != null;
    }

    @Test
    public void merged() {
        Merged merged = client.merged("btcusdt");
        assert merged != null;
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
    public void historyTrade() {
        Set<Trade> trades = client.historyTrade("btcusdt", 10);
        assert trades != null;
    }

    @Test
    public void orders() {
        Set<Order> orders = client.orders("btcusdt", null, null, null, Arrays.asList(OrderState.values()), null, null, null);
        assert orders != null;
    }

    @Test
    public void symbols() {
        Set<Symbol> orders = client.symbols();
        assert orders != null;
    }


    @Test
    public void currencies() {
        Set<String> currencys = client.currencys();
        assert currencys != null;
    }

    @Test
    public void getAccounts() {
        Set<Account> accounts = client.accounts();
        assert accounts != null;
    }

    @Test
    public void getBalance() {
        Account balance = client.balance("4880381");
        Asset btc = balance.getList().stream().filter(f -> f.getCurrency().equalsIgnoreCase("btc")).findFirst().get();
        Asset usdt = balance.getList().stream().filter(f -> f.getCurrency().equalsIgnoreCase("usdt")).findFirst().get();
        System.out.println(btc.getBalance().toPlainString());
        System.out.println(usdt.getBalance().toPlainString());
        assert balance != null;
    }

    @Test
    public void place() {
        Long id = client.place("4880381", "1", "1.1", OrderSource.API, "ETHBTC", OrderType.SELL_LIMIT);
        assert id != null;
    }


    @Test
    public void openOrders() {
        Set<Order> openOrders = client.openOrders("4880381", "btcusdt", null, 100);
        assert openOrders != null;
    }

    @Test
    public void get() {
        Order test = client.get("12664273590");
        assert test != null;
    }

    @Test
    public void cancel() {
        Long id = client.cancel("12654130771");
        assert id != null;
    }

    @Test
    public void batchCancel() {
        BatchCancelResp resp = client.batchCancel(Arrays.asList("12654130771", "12664273590"));
        assert resp != null;
    }


    @Test
    public void matchResultsByOrder() {
        Set<MatchResult> matchResults = client.matchResults("testId");
        assert matchResults != null;
    }

    @Test
    public void matchResults() {
        Set<MatchResult> matchResults = client.matchResults("btcusdt", null, null, null, null, null, null);
        assert matchResults != null;
    }


    @Test
    public void marketDetail() {
        Candle detail = client.detail("ltcusdt");
        log.info(String.valueOf(detail.getVol()));
    }


    @Test
    public void withdraw() {
        Long id = client.withdraw("aaaa", "1", "usdt", null, null);
        assert id != null;
    }

    @Test
    public void cancelWithdraw() {
        Long id = client.cancelWithdraw(111L);
        assert id != null;

    }

    @Test
    public void queryDepositWithdraw() {
        Set<DepositWithdraw> depositWithdraws = client.queryDepositWithdraw("usdt", "deposit", "", 1);
        assert depositWithdraws != null;

    }

    @Test
    public void transferIn() {
        Long id = client.transferInMargin("btcusdt", "usdt", "1");
        assert id != null;
    }

    @Test
    public void transferOut() {
        Long id = client.transferOutMargin("btcusdt", "usdt", "1");
        assert id != null;
    }


    @Test
    public void marginOrders() {
        Long id = client.marginOrders("btcusdt", "usdt", "1");
        assert id != null;
    }

    @Test
    public void marginOrderRepay() {
        Long id = client.marginOrderRepay("111", "1");
        assert id != null;
    }


    @Test
    public void loanOrder() {
        Set<LoanOrder> loanOrder = client.loanOrders("btcusdt", "2018-09-09", "2018-09-20", null, null, null, null);
        assert loanOrder != null;
    }


    @Test
    public void marginBlance() {
        Set<MarginAccount> account = client.marginBalance("btcusdt");
        assert account != null;
    }
}