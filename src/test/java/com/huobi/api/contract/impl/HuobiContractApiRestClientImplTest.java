package com.huobi.api.contract.impl;

import com.huobi.api.client.constant.HuobiConfig;
import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.domain.Merged;
import com.huobi.api.client.domain.Trade;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.resp.RespTick;
import com.huobi.api.contract.HuobiContractApiRestClient;
import com.huobi.api.contract.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * created by jacky. 2018/11/30 11:51 AM
 */
public class HuobiContractApiRestClientImplTest {

    private String apiKey = "a";
    private String apiSecret = "s";
    private HuobiContractApiRestClient client;

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        //unnecessary
        HuobiConfig.API_HOST = HuobiConsts.API_HOST_PRO;
        HuobiConfig.REST_API_URL = "https://api.hbdm.com";
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        client = new HuobiContractApiRestClientImpl(apiKey, apiSecret);
    }

    @Test
    public void info() {
        List<ContractInfo> info = client.info(null, null, null);
        assert info != null;
    }


    @Test
    public void index() {
        List<ContractIndex> btc = client.index("btc");
    }

    @Test
    public void priceLimit() {
        List<ContractPriceLimit> btc = client.priceLimit("btc", null, null);
    }

    @Test
    public void openInterest() {
        List<Interest> btc = client.openInterest("btc", null, null);
    }

    @Test
    public void deliveryPrice() {
        Delivery data = client.deliveryPrice("BTC");
        assert data != null;
    }


    @Test
    public void marketDepth() {
        Depth dep = client.marketDepth("BTC_CW", MergeLevel.STEP0);
        assert dep != null;
    }

    @Test
    public void historyKline() {
        List<Candle> data = client.historyKline("BTC_CW", Resolution.M1, 10);
        assert data != null;
    }

    @Test
    public void mergedMarketDetail() {
        Merged data = client.marketDetailMerged("BTC_CW");
        assert data != null;
    }

    @Test
    public void marketTrade() {
        RespTick<Trade> data = client.marketTrade("BTC_CW");
        assert data != null;
    }

    @Test
    public void historyTrade() {
        List<RespTick<Trade>> data = client.historyTrade("BTC_CQ", 10);
        assert data != null;
    }


    @Test
    public void accountInfo() {
        List<ContractAccount> data = client.accountInfo("BTC_CW");
        assert data != null;
    }
}
