package com.huobi.api.client;

import com.huobi.api.client.constant.HuobiConsts;
import com.huobi.api.client.domain.*;
import com.huobi.api.client.domain.reqs.PlaceOrderRequest;
import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.client.domain.resp.RespTick;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;
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
    Call<RespBody<Map>> batchCancel(@Query("order-ids") List<String> orderId);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/orders/{order-id}/matchresults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Path("order-id") String orderId);


    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/order/matchresults")
    Call<RespBody<Set<MatchResult>>> matchResults(@Query("symbol") String symbol, @Query("types") String types,
                                                  @Query("start-date") String startDate, @Query("end-date") String endDate,
                                                  @Query("from") String from, @Query("direct") String direct, @Query("size") Integer size);

    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v1/margin/accounts/balance")
    Call<RespBody<MarginAccount>> marginBalance(@Query("symbol") String symbol);

    //---- 以下 未经过测试-----

    /**
     * 虚拟币提现API
     *
     * @param address  提现地址
     * @param amount   提币数量
     * @param currency 资产类型.btc, ltc, bch, eth, etc ...(火币Pro支持的币种)
     * @param fee      optional  转账手续费
     * @param addrTag  optional  虚拟币共享地址tag，适用于xrp，xem，bts，steem，eos，xmr.  格式, "123"类的整数字符串
     * @return
     */
    @Headers(HuobiConsts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v1/dw/withdraw/api/create")
    Call<RespBody<Long>> withdraw(@Query("address") String address, @Query("amount") String amount, @Query("currency") String currency, @Query("fee") String fee, @Query("addr-tag") String addrTag);

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
}
