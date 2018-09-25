package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 4:12 PM
 */
@Getter
@Setter
public class OrderEventNotify {
    private OrderTick data;
}
