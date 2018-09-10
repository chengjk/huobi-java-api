package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.HuobiApiService;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.enums.*;
import com.huobi.api.client.domain.reqs.PlaceOrderRequest;
import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.client.domain.resp.RespTick;
import okhttp3.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static com.huobi.api.client.HuobiApiServiceGenerator.createService;
import static com.huobi.api.client.HuobiApiServiceGenerator.executeSync;

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
    public Set<Candle> kline(String symbol, Resolution period, Integer size) {
        return executeSync(service.kline(symbol, period == null ? null : period.getCode(), size)).getData();
    }

    @Override
    public Merged merged(String symbol) {
        return executeSync(service.merged(symbol)).getTick();
    }

    @Override
    public Set<Candle> tickers() {
        return executeSync(service.tickers()).getData();
    }

    @Override
    public Depth depth(String symbol, MergeLevel type) {
        return executeSync(service.depth(symbol, type == null ? null : type.getCode())).getTick();
    }

    @Override
    public Set<Trade> trade(String symbol) {
        return executeSync(service.trade(symbol)).getTick().getData();
    }

    @Override
    public Set<Trade> historyTrade(String symbol, Integer size) {
        RespBody<Set<RespTick<Trade>>> setRespBody = executeSync(service.historyTrade(symbol, size));
        Set<Trade> trades = new HashSet<>();
        for (RespTick<Trade> tick : setRespBody.getData()) {
            trades.addAll(tick.getData());
        }
        return trades;
    }

    @Override
    public Candle detail(String symbol) {
        return executeSync(service.detail(symbol)).getTick();
    }

    @Override
    public Set<Symbol> symbols() {
        return executeSync(service.symbols()).getData();
    }

    @Override
    public Set<String> currencys() {
        return executeSync(service.currencys()).getData();
    }

    @Override
    public Long timestamp() {
        return executeSync(service.timestamp()).getData();
    }

    @Override
    public Set<Account> accounts() {
        return executeSync(service.accounts()).getData();
    }

    @Override
    public Set<Order> orders(String symbol, List<OrderType> types, String startDate, String endDate, List<OrderState> states, String from, String direct, Integer size) {
        String typesStr = null;
        String stateStr = null;
        if (types != null && !types.isEmpty()) {
            StringJoiner typeJoiner = new StringJoiner(",");
            for (OrderType type : types) {
                typeJoiner.add(type.getCode());
            }
            typesStr = typeJoiner.toString();
        }

        if (states != null && !states.isEmpty()) {
            StringJoiner stateJoiner = new StringJoiner(",");
            for (OrderState state : states) {
                stateJoiner.add(state.getCode());
            }
            stateStr = stateJoiner.toString();
        }
        return executeSync(service.orders(symbol, typesStr, startDate, endDate, stateStr, from, direct, size)).getData();
    }


    @Override
    public Account balance(String accountId) {
        return executeSync(service.balance(accountId)).getData();
    }

    @Override
    public Long place(String accountId, String amount, String price, OrderSource source, String symbol, OrderType type) {
        PlaceOrderRequest req = new PlaceOrderRequest();
        req.setAccountId(accountId);
        req.setAmount(amount);
        req.setPrice(price);
        req.setSource(source == null ? null : source.getCode());
        req.setSymbol(symbol);
        req.setType(type == null ? null : type.getCode());
        return executeSync(service.place(req)).getData();
    }

    @Override
    public Set<Order> openOrders(String accountId, String symbol, OrderSide side, Integer size) {
        String name = null;
        if (side != null) {
            name = side.name();
        }
        return executeSync(service.openOrders(accountId, symbol.toLowerCase(), name, size)).getData();
    }

    @Override
    public Order get(String orderId) {
        return executeSync(service.get(orderId)).getData();
    }

    @Override
    public Long cancel(String orderId) {
        return executeSync(service.cancel(orderId)).getData();
    }

    @Override
    public Set<MatchResult> matchResults(String orderId) {
        return executeSync(service.matchResults(orderId)).getData();
    }


    @Override
    public Set<MatchResult> matchResults(String symbol, List<OrderType> types,
                                         String startDate, String endDate,
                                         String from, String direct, Integer size) {

        String typesStr = null;
        if (types != null && !types.isEmpty()) {
            StringJoiner typeJoiner = new StringJoiner(",");
            for (OrderType type : types) {
                typeJoiner.add(type.getCode());
            }
            typesStr = typeJoiner.toString();
        }

        return executeSync(service.matchResults(symbol, typesStr, startDate, endDate, from, direct, size)).getData();
    }


    @Override
    public MarginAccount marginBalance(String symbol) {
        return executeSync(service.marginBalance(symbol)).getData();
    }


    private String joinList(List<String> types, String delimiter) {
        String result = null;
        if (types != null && !types.isEmpty()) {
            StringJoiner joiner = new StringJoiner(delimiter);
            for (String type : types) {
                joiner.add(type);
            }
            result = joiner.toString();
        }
        return result;
    }


}
