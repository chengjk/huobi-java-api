package com.huobi.api.client.impl;

import com.huobi.api.client.domain.enums.MergeLevel;
import com.huobi.api.client.domain.enums.Resolution;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
public class HuobiApiWebSocketClientImplTest {
    private HuobiApiWebSocketClientImpl ws = new HuobiApiWebSocketClientImpl();
    Closeable stream;

    @Test
    public void onKlineTick() {
        stream = ws.onKlineTick("BTCUSDT", Resolution.M1, data -> {
            if (StringUtils.isNotEmpty(data.getSubbed())) {
                System.out.println(data.getSubbed());
            } else {
                System.out.println(data.getTick().getClose());
            }
        });

    }

    @Test
    public void onDepthTick() {
        stream = ws.onDepthTick("BTCUSDT", MergeLevel.STEP0, data -> {
            System.out.println(data.getRep());
        });
    }


    @After
    public void after() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000L);
        }
        stream.close();
    }
}