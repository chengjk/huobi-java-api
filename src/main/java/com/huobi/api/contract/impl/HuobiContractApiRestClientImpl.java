package com.huobi.api.contract.impl;

import com.huobi.api.contract.HuobiContractApiRestClient;
import com.huobi.api.contract.HuobiContractApiService;
import com.huobi.api.contract.domain.ContractInfo;

import java.util.List;

import static com.huobi.api.client.HuobiApiServiceGenerator.createService;
import static com.huobi.api.client.HuobiApiServiceGenerator.executeSync;

/**
 * created by jacky. 2018/11/30 11:43 AM
 */
public class HuobiContractApiRestClientImpl implements HuobiContractApiRestClient {
    private HuobiContractApiService service;

    public HuobiContractApiRestClientImpl(String apiKey, String secretKey) {
        service = createService(HuobiContractApiService.class, apiKey, secretKey);
    }

    @Override
    public List<ContractInfo> info(String symbol, String type, String code) {
        return executeSync(service.info(symbol, type, code)).getData();
    }


}
