package com.huobi.api.client.domain;

import java.util.List;

/**
 * created by jacky. 2018/7/23 4:08 PM
 */
public class MarginAccount {


    String id;
    String type;
    String state;
    List<Asset> list;

    String symbol;
    String fl_price;
    String fl_type;
    String risk_rate;
}
