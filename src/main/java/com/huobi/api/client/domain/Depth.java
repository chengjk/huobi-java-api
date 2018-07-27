package com.huobi.api.client.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
