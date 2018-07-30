package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.AccountState;
import com.huobi.api.client.domain.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/7/23 3:29 PM
 */
@Getter
@Setter
public class Account {
    private long id;
    private AccountType type;
    private AccountState state;
    @JsonProperty("user-id")
    private String userId;
    private List<Asset> list;

    private String subtype;
}
