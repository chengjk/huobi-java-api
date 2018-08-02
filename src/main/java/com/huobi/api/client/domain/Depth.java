package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by jacky. 2018/7/21 2:28 PM
 */
@Getter
@Setter
public class Depth {
    private long id;
    private long ts;
    private List<List<BigDecimal>> bids;
    private List<List<BigDecimal>> asks;

    private long version;
}
