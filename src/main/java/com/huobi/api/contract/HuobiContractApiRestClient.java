package com.huobi.api.contract;

import com.huobi.api.contract.domain.ContractInfo;

import java.util.List;

/**
 * created by jacky. 2018/11/30 11:43 AM
 */
public interface HuobiContractApiRestClient {


    List<ContractInfo> info(String symbol, String type, String code);
}
