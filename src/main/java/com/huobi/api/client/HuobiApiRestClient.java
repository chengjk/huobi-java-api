package com.huobi.api.client;

import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.resp.RespTick;

import java.util.List;
import java.util.Set;

/**
 * created by jacky. 2018/7/20 9:03 PM
 */
public interface HuobiApiRestClient {


    /**
     * 获取K线数据
     */
    Set<Candle> kline(String symbol, Resolution period, int size);

    /**
     * 获取聚合行情(Ticker)
     */
    Merged merged(String symbol);

    /**
     * 获取市场行情
     */
    Set<Candle> tickers();

    /**
     * 获取 Market Depth 数据
     */
    Depth depth(String symbol, MergeLevel type);


    /**
     * 单个symbol最新成交记录
     *
     * @param symbol
     * @return
     */
    Set<Trade>trade(String symbol);

    /**
     * 单个symbol批量成交记录
     *
     * @param symbol
     * @param size
     * @return
     */
    Trade historyTrade(String symbol, int size);

    /**
     * 滚动24小时交易聚合行情(单个symbol)
     *
     * @param symbol
     * @return
     */
    Candle detail(String symbol);


    /**
     * 交易品种的计价货币和报价精度
     *
     * @return
     */
    Set<Symbol> symbols();

    /**
     * 交易币种列表
     *
     * @return
     */
    Set<String> currencys();

    /**
     * 查询当前系统时间
     *
     * @return
     */
    Long timestamp();

    /**
     * 查询用户的所有账户状态
     *
     * @param id
     * @param state
     * @param type
     * @return
     */
    Set<AccountStates> accounts(String id, AccountStates state, AccountType type);

    /**
     * 查询用户当前委托、或历史委托订单 (up to 100)
     * @param symbol    交易对
     * @param types     查询的订单类型组合，使用','分割
     * @param startDate yyyy-mm-dd
     * @param endDate   yyyy-mm-dd
     * @param states    查询的订单状态组合，使用','分割
     * @param from      查询起始 ID
     * @param direct    prev,next
     * @param size
     * @return
     */
    Set<Order> orders(String symbol, List<OrderType> types,
                      String startDate, String endDate,
                      List<OrderState> states,
                      String from, String direct, String size);

    /**
     * 查询用户当前委托、或历史委托订单 (up to 100)
     *
     * @param symbol
     * @param status
     * @return
     */
    Set<Order> orders(String symbol, OrderState status);

    /**
     * @param accountId
     * @return
     */
    Account balance(String accountId);


    /**
     * 下单
     *
     * @param accountId
     * @param amount
     * @param price
     * @param source
     * @param symbol
     * @param type
     * @return
     */
    Long place(String accountId, String amount, String price,
               OrderSource source, String symbol, OrderType type);


    /**
     * 查询用户当前未成交订单 (up to 500)
     *
     * @param accountId
     * @param symbol
     * @param side
     * @param size      [0,500]
     * @return
     */
    Set<Order> openOrders(String accountId, String symbol, OrderSide side, int size);


    /**
     * 获取指定订单
     *
     * @param orderId
     * @return
     */
    Order get(String orderId);

    /**
     * 按order-id撤销一个订单
     *
     * @param orderId
     * @return
     */
    Long cancel(String orderId);


    /**
     * 根据order-id查询订单的成交明细
     */
    Set<Order> matchresults(String orderId);


    /**
     * 借贷账户详情
     */
    Set<Order> marginBalance(String symbol);


}
