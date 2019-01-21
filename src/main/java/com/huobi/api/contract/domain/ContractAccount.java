package com.huobi.api.contract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * String reated by jacky. 2019/1/21 ;//20 PM
 */
@Getter
@Setter
public class ContractAccount {
    private String symbol;    //品种代码"BTC","ETH"...
    @JsonProperty("margin_balance")
    private BigDecimal marginBalance;      //账户权益
    @JsonProperty("margin_position")
    private String marginPosition;     //持仓保证金（当前持有仓位所占用的保证金）
    @JsonProperty("margin_frozen")
    private BigDecimal marginFrozen;     //冻结保证金
    @JsonProperty("margin_available")
    private BigDecimal marginAvailable;    //可用保证金
    @JsonProperty("profit_real")
    private BigDecimal profitReal;     //已实现盈亏
    @JsonProperty("profit_unreal")
    private BigDecimal profitUnreal;    //未实现盈亏
    @JsonProperty("withdraw_available")
    private BigDecimal withdrawAvailable;    //保证金率
    @JsonProperty("risk_rate")
    private Integer riskRate;    //预估强平价
    @JsonProperty("liquidation_price")
    private BigDecimal liquidationPrice;    //可划转数量
    @JsonProperty("lever_rate")
    private BigDecimal leverRate;     //杠杠倍数
}
