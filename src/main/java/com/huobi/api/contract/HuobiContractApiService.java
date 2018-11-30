package com.huobi.api.contract;

import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.contract.domain.ContractIndex;
import com.huobi.api.contract.domain.ContractInfo;
import com.huobi.api.contract.domain.ContractPriceLimit;
import com.huobi.api.contract.domain.Interest;
import retrofit2.Call;
import retrofit2.http.GET;
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


}
