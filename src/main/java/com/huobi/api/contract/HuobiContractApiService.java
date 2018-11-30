package com.huobi.api.contract;

import com.huobi.api.client.domain.resp.RespBody;
import com.huobi.api.contract.domain.ContractInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * created by jacky. 2018/11/30 11:31 AM
 */
public interface HuobiContractApiService {

    /**
     * 获取 Market Depth 数据
     */
    @GET("api/v1/contract_contract_info")
    Call<RespBody<List<ContractInfo>>> info(@Query("symbol") String symbol, @Query("contract_type") String type, @Query("contract_code") String code);

}
