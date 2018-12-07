package com.huobi.api.client;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;

import java.io.Closeable;
import java.util.List;

/**
 * created by jacky. 2018/7/24 3:52 PM
 */
public interface HuobiApiWebSocketClient {
    Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback);

    Closeable onKlineTick(List<String> symbols, List<Resolution> resolutions, ApiCallback<KlineEventResp> callback);

    /**
     * 请求历史kline数据,一次最多300条.
     *
     * @param symbol
     * @param period
     * @param from     optional, type: long, 2017-07-28T00:00:00+08:00 至 2050-01-01T00:00:00+08:00 之间的时间点，单位：秒
     * @param to       optional, type: long, 2017-07-28T00:00:00+08:00 至 2050-01-01T00:00:00+08:00 之间的时间点，单位：秒，必须比 from 大
     * @param callback
     * @return
     */
    Closeable requestKline(String symbol, Resolution period, long from, long to, ApiCallback<KlineEventResp> callback);

    Closeable onDepthTick(String symbol, MergeLevel level, ApiCallback<DepthEventResp> callback);

    Closeable onDepthTick(List<String> symbols, List<MergeLevel> levels, ApiCallback<DepthEventResp> callback);

    Closeable requestDepth(String symbol, MergeLevel level, long from, long to, ApiCallback<DepthEventResp> callback);

    Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback);

    Closeable onTradeDetailTick(List<String> symbols, ApiCallback<TradeDetailResp> callback);

    Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback);

    Closeable onMarketDetailTick(List<String> symbols, ApiCallback<MarketDetailResp> callback);

    /**
     * @param symbol   pattern  *
     * @param callback
     * @return
     */
    Closeable onOrderTick(String symbol, ApiCallback<OrderEventResp> callback);

    Closeable onAccountTick(ApiCallback<AccountEventResp> callback);
}
