package com.huobi.api.contract;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.contract.domain.ContractIndex;
import com.huobi.api.contract.domain.ContractInfo;
import com.huobi.api.contract.domain.ContractPriceLimit;
import com.huobi.api.contract.domain.Interest;

import java.util.List;

/**
 * created by jacky. 2018/11/30 11:43 AM
 */
public interface HuobiContractApiRestClient {

    List<ContractInfo> info(String symbol, String type, String code);

    List<ContractIndex> index(String symbol);

    List<ContractPriceLimit> priceLimit(String symbol, String type, String code);

    List<Interest> openInterest(String symbol, String type, String code);

    String marketDepth(String symbol, MergeLevel type);
}
