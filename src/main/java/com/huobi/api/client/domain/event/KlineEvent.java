package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.enums.Resolution;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 4:16 PM
 */
@Getter
@Setter
public class KlineEvent implements WsEvent {
    String symbol;
    Resolution period;

    @Override
    public String toSubscribe()  {
        String source = "{  \"sub\": \"market.%s.kline.%s\",  \"id\": \"%s\"}";
        return String.format(source, symbol.toLowerCase(), period.getCode(), "kline_" + symbol.toLowerCase() + "_" + period.getCode());
    }
}
