package com.huobi.api.contract.domain;

import com.huobi.api.client.domain.resp.RespBody;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by jacky. 2018/11/30 2:41 PM
 */
@Getter
@Setter
public class Depth extends RespBody {
    private List<List<BigDecimal>> bids;
    private List<List<BigDecimal>> asks;
    private String ch;
    private Long id;
    private Long mrid;
    private Long ts;
    private Long version;

}
