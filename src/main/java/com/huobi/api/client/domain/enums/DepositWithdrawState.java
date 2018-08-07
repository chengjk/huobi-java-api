package com.huobi.api.client.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by jacky. 2018/8/7 10:18 AM
 */

@Getter
@AllArgsConstructor
public enum DepositWithdrawState {

    //deposit
    SUBMITTED("submitted"),    //已提交
    REEXAMINE("reexamine"),    //审核中
    CANCELED("canceled"),    //已撤销
    PASS("pass"),    //审批通过
    REJECT("reject"),    //审批拒绝
    PRE_TRANSFER("pre_transfer"),    //处理中
    WALLET_TRANSFER("wallet_transfer"),    //已汇出
    WALLET_REJECT("wallet_reject"),    //钱包拒绝
    CONFIRMED("confirmed"),    //区块已确认
    CONFIRM_ERROR("confirm_error"),    //区块确认错误
    REPEALED("repealed"),   //已撤销
    //withdraw
    UNKNOWN("unknown"),//	状态未知
    CONFIRMING("confirming"),//	确认中
//    CONFIRMED("confirmed"),//	确认中
    SAFE("safe"),//	已完成
    ORPHAN("orphan");//

    private String code;

    @JsonValue
    public String getCode() {
        return code;
    }
}
