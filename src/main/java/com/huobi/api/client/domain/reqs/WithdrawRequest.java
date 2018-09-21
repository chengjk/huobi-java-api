package com.huobi.api.client.domain.reqs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/9/21 3:36 PM
 */
@Getter
@Setter
@AllArgsConstructor
public class WithdrawRequest {
    //提现地址.仅支持在官网上相应币种可信地址列表 中的地址
    private String address;
    //提币数量
    private String amount;
    //资产类型		btc, ltc, bch, eth, etc ...(火币Pro支持的币种)
    private String currency;
    //转账手续费 optional
    private String fee;
    //虚拟币共享地址tag。optional。适用于xrp，xem，bts，steem，eos，xmr
    @JsonProperty("addr-tag")
    private String addrTag;

    public WithdrawRequest(String address, String amount, String currency) {
        this.address = address;
        this.amount = amount;
        this.currency = currency;
    }
}
