package com.huobi.api.contract;

import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.domain.Merged;
import com.huobi.api.client.domain.Trade;
import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.client.domain.resp.RespTick;
import com.huobi.api.contract.domain.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * created by jacky. 2018/11/30 11:31 AM
 */
public interface HuobiContractApiService {

    @GET("api/v1/contract_contract_info")
    Call<RespBody<List<ContractInfo>>> info(@Query("symbol") String symbol, @Query("contract_type") String type, @Query("contract_code") String code);

    @GET("api/v1/contract_index")
    Call<RespBody<List<ContractIndex>>> index(@Query("symbol") String symbol);

    @GET("api/v1/contract_price_limit")
    Call<RespBody<List<ContractPriceLimit>>> priceLimit(@Query("symbol") String symbol, @Query("contract_type") String type, @Query("contract_code") String code);

    @GET("api/v1/contract_open_interest")
    Call<RespBody<List<Interest>>> openInterest(@Query("symbol") String symbol, @Query("contract_type") String type, @Query("contract_code") String code);


    @GET("api/v1/contract_delivery_price")
    Call<RespBody<Delivery>> deliveryPrice(@Query("symbol") String symbol);

    @GET("market/depth")
    Call<RespBody<Depth>> marketDepth(@Query("symbol") String symbol, @Query("type") String type);

    @GET("market/history/kline")
    Call<RespBody<List<Candle>>> historyKline(@Query("symbol") String symbol, @Query("period") String period, @Query("size") Integer size);

    @GET("market/detail/merged")
    Call<RespBody<Merged>> marketDetailMerged(@Query("symbol") String symbol);

    @GET("market/trade")
    Call<RespBody<RespTick<Trade>>> marketTrade(@Query("symbol") String symbol);

    @GET("market/history/trade")
    Call<RespBody<List<RespTick<Trade>>>> historyTrade(@Query("symbol") String symbol, @Query("size") Integer size);

    /**
     * @param symbol "BTC","ETH"...如果缺省，默认返回所有品种
     * @return
     */
    @POST("api/v1/contract_account_info")
    Call<RespBody<List<ContractAccount>>> accountInfo(@Query("symbol") String symbol);


}
