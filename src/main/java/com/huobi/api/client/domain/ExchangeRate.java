package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2019/7/8 10:32 AM
 */
@Getter
@Setter
public class ExchangeRate {
    private String currency;
    @JsonProperty("buy-rate")
    private String buyRate;
    @JsonProperty("buy-quota")
    private String buyQuota;
    @JsonProperty("sell-rate")
    private String sellRate;
    @JsonProperty("sell-quota")
    private String sellQuota;
    private int state;
    private long time;

}
