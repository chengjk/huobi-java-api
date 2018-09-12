package com.huobi.api.client;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.DepthEventResp;
import com.huobi.api.client.domain.event.KlineEventResp;
import com.huobi.api.client.domain.event.MarketDetailResp;
import com.huobi.api.client.domain.event.TradeDetailResp;
import com.huobi.api.client.domain.resp.ApiCallback;

import java.io.Closeable;

/**
 * created by jacky. 2018/7/24 3:52 PM
 */
public interface HuobiApiWebSocketClient {
    Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback);

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

    Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback);

    Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback);
}
