package com.huobi.api.client;

import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.*;
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
    Call<RespBody<RespTick<Set<Trade>>>> trade(@Query("symbol") String symbol);

    @GET("/market/history/trade")
    Call<RespBody<Set<Trade>>> historyTrade(@Query("symbol") String symbol, @Query("size") int size);

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
    Call<RespBody<Long>> place(@Query("account-id") String accountId, @Query("amount") String amount, @Query("price") String price,
                               @Query("source") String source, @Query("symbol") String symbol, @Query("type") String type);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/openOrders")
    Call<RespBody<Set<Order>>> openOrders(@Query("account-id") String accountId, @Query("symbol") String symbol, @Query("side") String side, @Query("size") int size);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/{order-id}")
    Call<RespBody<Order>> get(@Query("order-id") String orderId);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/order/orders/{order-id}/submitcancel")
    Call<RespBody<Long>> cancel(@Query("order-id") String orderId);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/{order-id}/matchResults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Query("order-id") String orderId);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/matchResults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Query("symbol") String symbol, @Query("types") String types,
                                                  @Query("start-date") String startDate, @Query("end-date") String endDate,
                                                  @Query("from") String from, @Query("direct") String direct, @Query("size") Integer size);
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/margin/accounts/balance")
    Call<RespBody<MarginAccount>> marginBalance(@Query("symbol") String symbol);


}
