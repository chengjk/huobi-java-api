package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.MergeLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 8:39 PM
 */
@Getter
@Setter
public class DepthEvent extends WsBaseEvent {
    private String symbol;
    private MergeLevel level;
    private long from;
    private long to;


    @Override
    public String toSubscribe() {
        String sub = "{\n" +
                "\"sub\": \"%s\",\n" +
                "\"id\": \"%s\"\n" +
                "}";
        return String.format(sub, getTopic(), "depth_" + symbol.toLowerCase() + "_" + level.getCode());
    }


    public String toRequest() {
        String req = "{\n" +
                "  \"req\": \"%s\",\n" +
                "  \"id\": \"%s\",\"from\": %s,\"to\": %s}" +
                "}";
        return String.format(req, getTopic(), "depth_" + symbol.toLowerCase() + "_" + level.getCode(), from, to);
    }



    public String getTopic() {
        return String.format("market.%s.depth.%s", symbol.toLowerCase(), level.getCode());
    }

    @Override
    public String getClientId() {
        return System.currentTimeMillis() + "";
    }
}
