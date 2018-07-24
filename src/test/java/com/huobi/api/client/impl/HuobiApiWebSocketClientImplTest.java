package com.huobi.api.client.impl;

import com.huobi.api.client.domain.enums.Resolution;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
public class HuobiApiWebSocketClientImplTest {

    @Test
    public void onKlineTick() throws InterruptedException, IOException {
        HuobiApiWebSocketClientImpl ws = new HuobiApiWebSocketClientImpl();
        Closeable klineStream = ws.onKlineTick("BTCUSDT", Resolution.M1, data -> {
            if (StringUtils.isNotEmpty(data.getSubbed())) {
                System.out.println(data.getSubbed());
            }else {
                System.out.println(data.getTick().getClose());
            }
        });
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000L);
        }
        klineStream.close();
    }
}