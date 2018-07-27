package com.huobi.api.client;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import com.huobi.api.client.domain.event.*;
import com.huobi.api.client.domain.resp.ApiCallback;

import java.io.Closeable;

/**
 * created by jacky. 2018/7/24 3:52 PM
 */
public interface HuobiApiWebSocketClient {
    Closeable onKlineTick(String symbol, Resolution period, ApiCallback<KlineEventResp> callback);

    Closeable onDepthTick(String symbol, MergeLevel level, ApiCallback<DepthEventResp> callback);

    Closeable onTradeDetailTick(String symbol, ApiCallback<TradeDetailResp> callback);

    Closeable onMarketDetailTick(String symbol, ApiCallback<MarketDetailResp> callback);
}
