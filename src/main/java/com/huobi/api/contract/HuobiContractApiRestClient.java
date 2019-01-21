package com.huobi.api.contract;

import com.huobi.api.client.domain.Candle;
import com.huobi.api.client.domain.Merged;
import com.huobi.api.client.domain.Trade;
import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.resp.RespTick;
import com.huobi.api.contract.domain.*;

import java.util.List;

/**
 * created by jacky. 2018/11/30 11:43 AM
 */
public interface HuobiContractApiRestClient {

    List<ContractInfo> info(String symbol, String type, String code);

    List<ContractIndex> index(String symbol);

    List<ContractPriceLimit> priceLimit(String symbol, String type, String code);

    List<Interest> openInterest(String symbol, String type, String code);

    Delivery deliveryPrice(String symbol);

    Depth marketDepth(String symbol, MergeLevel type);

    List<Candle> historyKline(String symbol, Resolution period, Integer size);

    Merged marketDetailMerged(String symbol);

    RespTick<Trade> marketTrade(String symbol);

    List<RespTick<Trade>> historyTrade(String symbol, Integer size);

    List<ContractAccount> accountInfo(String symbol);
}
