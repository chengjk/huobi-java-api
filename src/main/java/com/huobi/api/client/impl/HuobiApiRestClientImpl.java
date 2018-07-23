package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.HuobiApiService;
import com.huobi.api.client.domain.Kline;
import com.huobi.api.client.domain.Order;
import com.huobi.api.client.domain.OrderStatus;
import com.huobi.api.client.domain.Trade;
import com.huobi.api.client.domain.resp.RespBody;

import java.util.Set;

import static com.huobi.api.client.HuobiApiServiceGenerater.createService;
import static com.huobi.api.client.HuobiApiServiceGenerater.executeSync;

/**
 * created by jacky. 2018/7/20 8:58 PM
 */
public class HuobiApiRestClientImpl implements HuobiApiRestClient {

    private HuobiApiService service;
    private String apiKey;
    private String apiSecret;

    public HuobiApiRestClientImpl(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        service = createService(HuobiApiService.class, apiKey, apiSecret);
    }

    @Override
    public long timestamp() {
        return executeSync(service.timestamp());
    }

    @Override
    public Set<Kline> tickers() {
        return executeSync(service.tickers()).getData();
    }

    @Override
    public Set<Kline> kline(String symbol, String period, int size) {
        return executeSync(service.kline(symbol,period,size)).getData();
    }


    @Override
    public Set<Trade> trade(String symbol) {
        return executeSync(service.trade(symbol)).getTick().getData();
    }


    @Override
    public Set<Order> orders(String symbol, OrderStatus status) {
        RespBody<Set<Order>> resp = executeSync(service.orders(symbol, null, null, null, status.getCode(), null, null, null));
        return  resp.getData();

    }
}
