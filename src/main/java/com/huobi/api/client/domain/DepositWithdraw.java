package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huobi.api.client.domain.enums.DepositWithdrawState;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/8/7 10:16 AM
 */
@Getter
@Setter
public class DepositWithdraw {
    private Long id;
    //类型	'deposit' 'withdraw'
    private String type;
    //币种
    private String currency;
    @JsonProperty("tx-hash")
    //交易哈希
    private String txHash;
    //个数
    private String amount;
    //地址
    private String address;
    @JsonProperty("address-tag")
    //地址标签
    private String addressTag;
    //手续费
    private String fee;
    //状态
    private DepositWithdrawState state;
    @JsonProperty("created-at")
    //发起时间
    private long createdAt;
    @JsonProperty("updated-at")
    //最后更新时间
    private long updatedAt;
}
