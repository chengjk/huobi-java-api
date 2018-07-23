package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/7/23 3:29 PM
 */
@Getter
@Setter
public class Account {
    private String id;
    private String type;
    private String state;
    @JsonProperty("user-id")
    private String userId;
    private List<Asset> list;
}
