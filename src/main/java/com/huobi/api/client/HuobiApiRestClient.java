package com.huobi.api.client;

import com.huobi.api.client.domain.Kline;
import com.huobi.api.client.domain.Order;
import com.huobi.api.client.domain.OrderStatus;
import com.huobi.api.client.domain.Trade;

import java.util.Set;

/**
 * created by jacky. 2018/7/20 9:03 PM
 */
public interface HuobiApiRestClient {

    long timestamp();
    Set<Kline> tickers();

    Set<Kline> kline(String symbol, String period, int size);




    Set<Trade>  trade(String symbol);


    Set<Order> orders(String symbol, OrderStatus status);
}
