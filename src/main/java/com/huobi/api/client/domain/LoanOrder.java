package com.huobi.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/9/21 11:56 AM
 */
@Getter
@Setter
public class LoanOrder {
    //订单号
    private String id;
    //用户ID
    @JsonProperty("user-id")
    private String userId;
    //账户ID
    @JsonProperty("account-id")
    private String accountId;
    //交易对
    private String symbol;
    //币种
    private String currency;
    //借贷本金总额
    @JsonProperty("loan-amount")
    private BigDecimal loanAmount;
    //未还本金
    @JsonProperty("loan-balance")
    private BigDecimal loanBalance;
    //利率
    @JsonProperty("interest-rate")
    private BigDecimal interestRate;
    //利息总额
    @JsonProperty("interest-amount")
    private BigDecimal interestAmount;
    //未还利息
    @JsonProperty("interest-balance")
    private BigDecimal interestBalance;
    //借贷发起时间
    @JsonProperty("created-at")
    private Long createdAt;
    //最近一次计息时间
    @JsonProperty("accrued-at")
    private Long accruedAt;
    //订单状态. created 未放款，accrual 已放款，cleared 已还清，invalid 异常
    private String state;


}
