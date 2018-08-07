package com.huobi.api.client.domain;

import com.huobi.api.client.domain.enums.DepositWithdrawState;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/8/7 10:16 AM
 */
@Getter
@Setter
public class DepositWithdraw {
    private Long id;//true	long
    private String type;//true	long	类型	'deposit' 'withdraw'
    private String currency;//true	string	币种
    private String tx_hash;//true	string	交易哈希
    private String amount;//true	long	个数
    private String address;//true	string	地址
    private String address_tag;//true	string	地址标签
    private String fee;//true	long	手续费
    private DepositWithdrawState state;//true	string	状态	状态参见下表
    private long created_at;//true	long	发起时间
    private long updated_at;//
}
