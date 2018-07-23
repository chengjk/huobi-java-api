package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * created by jacky. 2018/7/21 2:28 PM
 */
@Getter
@Setter
public class Depth {
    private long id;
    private long ts;
    private Map<Long, Double> bids;
    private Map<Long, Double> asks;
}
