package com.huobi.api.client;

import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.enums.*;

import java.util.List;
import java.util.Set;

/**
 * created by jacky. 2018/7/20 9:03 PM
 */
public interface HuobiApiRestClient {

    /**
     * 获取K线数据
     *
     * @param symbol
     * @param period
     * @param size   optional default 150. [1.2000]
     * @return
     */

    Set<Candle> kline(String symbol, Resolution period, Integer size);

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
    Set<Trade> trade(String symbol);

    /**
     * 单个symbol批量成交记录
     *
     * @param symbol
     * @param size   optional default 1 [1,2000]
     * @return
     */
    Set<Trade> historyTrade(String symbol, Integer size);

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
     * @return
     */
    Set<Account> accounts();

    /**
     * 查询用户当前委托、或历史委托订单 (up to 100)
     *
     * @param symbol    交易对
     * @param types     optional, 查询的订单类型组合，使用','分割
     * @param startDate optional ,yyyy-mm-dd
     * @param endDate   optional,yyyy-mm-dd
     * @param states    查询的订单状态组合，使用','分割
     * @param from      optional, 查询起始 ID
     * @param direct    optional, prev,next
     * @param size      optional, [0,100]
     * @return
     */
    Set<Order> orders(String symbol, List<OrderType> types, String startDate, String endDate,
                      List<OrderState> states, String from, String direct, Integer size);


    /**
     * @param accountId
     * @return
     */
    Account balance(String accountId);

    /**
     * 下单
     *
     * @param accountId 账户 ID，使用accounts方法获得。币币交易使用‘spot’账户的accountid；借贷资产交易，请使用‘margin’账户的accountid
     * @param amount    限价单表示下单数量，市价买单时表示买多少钱，市价卖单时表示卖多少币
     * @param price     optional
     * @param source    optional
     * @param symbol
     * @param type
     * @return
     */
    Long place(String accountId, String amount, String price, OrderSource source, String symbol, OrderType type);

    /**
     *
     * “account-id” 和 “symbol” 需同时指定或者二者都不指定。如果二者都不指定，返回最多500条尚未成交订单，按订单号降序排列。
     * 查询用户当前未成交订单 (up to 500)
     *
     * @param accountId
     * @param symbol
     * @param side      optional
     * @param size      optional default 150. [0,500]
     * @return
     */
    Set<Order> openOrders(String accountId, String symbol, OrderSide side, Integer size);

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
    Set<MatchResult> matchResults(String orderId);

    Set<MatchResult> matchResults(String symbol, List<OrderType> types,
                                  String startDate, String endDate,
                                  String from, String direct, Integer size);

    /**
     * 借贷账户详情
     */
    MarginAccount marginBalance(String symbol);
}
