package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Depth;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 8:43 PM
 */
@Getter
@Setter
public class DepthEventResp extends WsNotify {
    private Depth tick;
}
