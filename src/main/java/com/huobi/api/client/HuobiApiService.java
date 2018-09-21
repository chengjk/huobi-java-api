package com.huobi.api.client;

import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.reqs.*;
import com.huobi.api.client.domain.resp.BatchCancelResp;
import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.client.domain.resp.RespTick;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Set;

/**
 * created by jacky. 2018/7/20 8:41 PM
 */
public interface HuobiApiService {


    /**
     * 获取K线数据
     */
    @GET("/market/history/kline")
    Call<RespBody<Set<Candle>>> kline(@Query("symbol") String symbol, @Query("period") String period, @Query("size") int size);

    /**
     * 获取聚合行情(Ticker)
     */
    @GET("/market/detail/merged")
    Call<RespBody<Merged>> merged(@Query("symbol") String symbol);

    /**
     * 获取市场行情
     */
    @GET("/market/tickers")
    Call<RespBody<Set<Candle>>> tickers();

    /**
     * 获取 Market Depth 数据
     */
    @GET("/market/depth")
    Call<RespBody<Depth>> depth(@Query("symbol") String symbol, @Query("type") String type);


    @GET("/market/trade")
    Call<RespBody<RespTick<Trade>>> trade(@Query("symbol") String symbol);

    @GET("/market/history/trade")
    Call<RespBody<Set<RespTick<Trade>>>> historyTrade(@Query("symbol") String symbol, @Query("size") int size);

    @GET("/market/detail")
    Call<RespBody<Candle>> detail(@Query("symbol") String symbol);


    @GET("/v1/common/symbols")
    Call<RespBody<Set<Symbol>>> symbols();

    @GET("/v1/common/currencys")
    Call<RespBody<Set<String>>> currencys();

    @GET("/v1/common/timestamp")
    Call<RespBody<Long>> timestamp();

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/account/accounts")
    Call<RespBody<Set<Account>>> accounts();

    /**
     * 查询用户当前委托、或历史委托订单 (up to 100)
     *
     * @param symbol
     * @param types
     * @param startDate
     * @param endDate
     * @param states
     * @param from
     * @param direct    prev,next
     * @param size      nullable
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders")
    Call<RespBody<Set<Order>>> orders(@Query("symbol") String symbol, @Query("types") String types,
                                      @Query("start-date") String startDate, @Query("end-date") String endDate,
                                      @Query("states") String states,
                                      @Query("from") String from, @Query("direct") String direct, @Query("size") Integer size);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/account/accounts/{account-id}/balance")
    Call<RespBody<Account>> balance(@Path("account-id") String accountId);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/order/orders/place")
    Call<RespBody<Long>> place(@Body PlaceOrderRequest request);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/openOrders")
    Call<RespBody<Set<Order>>> openOrders(@Query("account-id") String accountId, @Query("symbol") String symbol, @Query("side") String side, @Query("size") Integer size);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/{order-id}")
    Call<RespBody<Order>> get(@Path("order-id") String orderId);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/order/orders/{order-id}/submitcancel")
    Call<RespBody<Long>> cancel(@Path("order-id") String orderId);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/order/orders/batchcancel")
    Call<RespBody<BatchCancelResp>> batchCancel(@Body BatchCancelRequest request);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/{order-id}/matchresults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Path("order-id") String orderId);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/matchresults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Query("symbol") String symbol, @Query("types") String types,
                                                  @Query("start-date") String startDate, @Query("end-date") String endDate,
                                                  @Query("from") String from, @Query("direct") String direct, @Query("size") Integer size);

    /**
     * 虚拟币提现API
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/dw/withdraw/api/create")
    Call<RespBody<Long>> withdraw(@Body WithdrawRequest request);


    /**
     * 申请取消提现虚拟币
     *
     * @param withdrawId 提现ID，填在path中
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/dw/withdraw-virtual/{withdraw-id}/cancel")
    Call<RespBody<Long>> cancelWithdraw(@Path("withdraw-id") long withdrawId);


    /**
     * 查询虚拟币充提记录
     *
     * @param currency 币种
     * @param type     'deposit' or 'withdraw'
     * @param from     optional. 查询起始 ID
     * @param size     optional. 查询记录大小
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/query/deposit-withdraw")
    Call<RespBody<Set<DepositWithdraw>>> queryDepositWithdraw(@Query("currency") String currency, @Query("type") String type, @Query("from") String from, @Query("size") Integer size);


    //------杠杆交易----

    /**
     * 从币币交易账户划转至杠杆账户
     *
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/dw/transfer-in/margin")
    Call<RespBody<Long>> transferInMargin(@Body TransferMarginRequest request);

    /**
     * 从杠杆账户划转至币币交易账户
     *
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/dw/transfer-out/margin")
    Call<RespBody<Long>> transferOutMargin(@Body TransferMarginRequest request);

    /**
     * 申请借贷
     *
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/margin/orders")
    Call<RespBody<Long>> marginOrders(@Body TransferMarginRequest request);


    /**
     * 归还借贷
     *
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/margin/orders/{order-id}/repay")
    Call<RespBody<Long>> marginOrderRepay(@Path("order-id") String orderId, @Body MarginRepayRequest request);


    /**
     * 查询借贷记录
     *
     * @param symbol    交易对
     * @param startDate optional 查询开始日期, 日期格式yyyy-mm-dd
     * @param endDate   optional 查询结束日期, 日期格式yyyy-mm-dd
     * @param states    optional 状态
     * @param from      optional 查询起始 ID
     * @param direct    optional 查询方向.prev 向前，next 向后
     * @param size      optional 查询记录大小
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/margin/loan-orders")
    Call<RespBody<Set<LoanOrder>>> loanOrders(@Query("symbol") String symbol, @Query("start-date") String startDate, @Query("end-date") String endDate,
                                         @Query("states") String states, @Query("from") String from, @Query("direct") String direct, @Query("size") Integer size);

    /**
     * 借贷账户详情
     *
     * @param symbol 交易对
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/margin/accounts/balance")
    Call<RespBody<Set<MarginAccount>>> marginBalance(@Query("symbol") String symbol);
}
