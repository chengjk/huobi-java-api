package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/9/25 5:19 PM
 */
@Getter
@Setter
public class AccountTick {
    private String event;
    private List<Asset> list;
}
