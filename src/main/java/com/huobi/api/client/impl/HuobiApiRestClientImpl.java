package com.huobi.api.client.impl;

import com.huobi.api.client.HuobiApiRestClient;
import com.huobi.api.client.HuobiApiService;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.enums.*;
import com.huobi.api.client.domain.reqs.*;
import com.huobi.api.client.domain.resp.BatchCancelResp;
import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.client.domain.resp.RespTick;
import com.huobi.api.client.exception.HuobiApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

import static com.huobi.api.client.HuobiApiServiceGenerator.createService;
import static com.huobi.api.client.HuobiApiServiceGenerator.executeSync;

/**
 * created by jacky. 2018/7/20 8:58 PM
 */
public class HuobiApiRestClientImpl implements HuobiApiRestClient {

    private HuobiApiService service;

    public HuobiApiRestClientImpl(String apiKey, String apiSecret) {
        service = createService(HuobiApiService.class, apiKey, apiSecret);
    }


    @Override
    public List<Candle> kline(String symbol, Resolution period, Integer size) {
        return executeSync(service.kline(symbol, period == null ? null : period.getCode(), size)).getData();
    }

    @Override
    public Merged merged(String symbol) {
        return executeSync(service.merged(symbol)).getTick();
    }

    @Override
    public List<Candle> tickers() {
        return executeSync(service.tickers()).getData();
    }

    @Override
    public Depth depth(String symbol, MergeLevel type) {
        return executeSync(service.depth(symbol, type == null ? null : type.getCode())).getTick();
    }

    @Override
    public List<Trade> trade(String symbol) {
        return executeSync(service.trade(symbol)).getTick().getData();
    }

    @Override
    public List<Trade> historyTrade(String symbol, Integer size) {
        RespBody<List<RespTick<Trade>>> setRespBody = executeSync(service.historyTrade(symbol, size));
        List<Trade> trades = new ArrayList<>();
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
    public List<Symbol> symbols() {
        return executeSync(service.symbols()).getData();
    }

    @Override
    public List<String> currencys() {
        return executeSync(service.currencys()).getData();
    }

    @Override
    public Long timestamp() {
        return executeSync(service.timestamp()).getData();
    }

    @Override
    public List<Account> accounts() {
        return executeSync(service.accounts()).getData();
    }

    @Override
    public List<Order> orders(String symbol, List<OrderType> types, String startDate, String endDate, List<OrderState> states, String from, String direct, Integer size) {
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
    public List<Order> openOrders(String accountId, String symbol, OrderSide side, Integer size) {
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
    public BatchCancelResp batchCancel(List<String> orderIds) {

        BatchCancelRequest req = new BatchCancelRequest(new HashSet<>(orderIds));
        return executeSync(service.batchCancel(req)).getData();
    }

    @Override
    public List<MatchResult> matchResults(String orderId) {
        try {
            return executeSync(service.matchResults(orderId)).getData();
        } catch (HuobiApiException e) {
            if (e.getErrCode().equalsIgnoreCase("base-record-invalid")) {
                return new ArrayList<>();
            }
            throw e;
        }
    }


    @Override
    public List<MatchResult> matchResults(String symbol, List<OrderType> types,
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
        try {
            return executeSync(service.matchResults(symbol, typesStr, startDate, endDate, from, direct, size)).getData();
        } catch (HuobiApiException e) {
            if (e.getErrCode().equalsIgnoreCase("base-record-invalid")) {
                return new ArrayList<>();
            }
            throw e;
        }
    }


    @Override
    public Long withdraw(String address, String amount, String currency, String fee, String addrTag) {
        WithdrawRequest req = new WithdrawRequest(address, amount, currency);
        req.setFee(fee);
        req.setAddrTag(addrTag);
        return executeSync(service.withdraw(req)).getData();
    }

    @Override
    public Long cancelWithdraw(Long withdrawId) {
        return executeSync(service.cancelWithdraw(withdrawId)).getData();
    }


    @Override
    public List<DepositWithdraw> queryDepositWithdraw(String currency, String type, String from, Integer size) {
        return executeSync(service.queryDepositWithdraw(currency, type, from, size)).getData();
    }


    @Override
    public Long transferInMargin(String symbol, String currency, String amount) {
        TransferMarginRequest request = new TransferMarginRequest(symbol, currency, amount);
        return executeSync(service.transferInMargin(request)).getData();
    }

    @Override
    public Long transferOutMargin(String symbol, String currency, String amount) {
        TransferMarginRequest request = new TransferMarginRequest(symbol, currency, amount);
        return executeSync(service.transferOutMargin(request)).getData();
    }


    @Override
    public Long marginOrders(String symbol, String currency, String amount) {
        TransferMarginRequest request = new TransferMarginRequest(symbol, currency, amount);
        return executeSync(service.marginOrders(request)).getData();
    }


    @Override
    public Long marginOrderRepay(String orderId, String amount) {
        MarginRepayRequest request = new MarginRepayRequest(amount);
        return executeSync(service.marginOrderRepay(orderId, request)).getData();
    }


    @Override
    public List<LoanOrder> loanOrders(String symbol, String startDate, String endDate,
                                      String states, String from, String direct, Integer size) {
        return executeSync(service.loanOrders(symbol, startDate, endDate, states, from, direct, size)).getData();
    }


    @Override
    public List<MarginAccount> marginBalance(String symbol) {
        return executeSync(service.marginBalance(symbol)).getData();
    }


    @Override
    public List<ExchangeRate> stableExchangeRate() {
        return executeSync(service.stableExchangeRate()).getData();
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
