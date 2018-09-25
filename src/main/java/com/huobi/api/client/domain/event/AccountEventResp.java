package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/25 5:21 PM
 */
@Getter
@Setter
public class AccountEventResp extends WsNotify {
    private AccountTick data;
}
