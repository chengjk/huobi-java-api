package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.Resolution;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 4:16 PM
 */
@Getter
@Setter
public class KlineEvent extends WsBaseEvent {
    private String symbol;
    private Resolution period;
    private long from;
    private long to;


    @Override
    public String toSubscribe() {
        String source = "{  \"sub\": \"%s\",  \"id\": \"%s\"}";
        return String.format(source, getTopic(), "kline_" + symbol.toLowerCase() + "_*");
    }


    public String toRequest() {
        String source = "{  \"req\": \"%s\",  \"id\": \"%s\" , \"from\": %s,\"to\": %s}";
        return String.format(source, getTopic(), "kline_" + symbol.toLowerCase() + "_" + period.getCode(), from, to);
    }

    @Override
    public String getTopic() {
        return String.format("market.%s.kline.%s", symbol.toLowerCase(), period.getCode());
    }
}
