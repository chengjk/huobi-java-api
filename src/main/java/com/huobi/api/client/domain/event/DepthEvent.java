package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.MergeLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 8:39 PM
 */
@Getter
@Setter
public class DepthEvent implements WsEvent {

    private String symbol;
    private MergeLevel level;

    @Override
    public String toSubscribe() {
        String sub = "{\n" +
                "\"req\": \"market.%s.depth.%s\",\n" +
                "\"id\": \"%s\"\n" +
                "}";

        return String.format(sub, symbol.toLowerCase(), level.getCode(), "depth_" + symbol.toLowerCase() + "_" + level.getCode());
    }
}
