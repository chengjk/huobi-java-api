package com.huobi.api.client.domain.event;

import com.huobi.api.client.domain.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/9/25 5:19 PM
 */
@Getter
@Setter
public class AccountTick {
    /**
     * <p>订单创建(order.place)</p>
     * <p>订单成交(order.match)</p>
     * <p>订单成交退款（order.refund)</p>
     * <p>订单撤销(order.cancel)</p>
     * <p>点卡抵扣交易手续费（order.fee-refund)</p>
     * <p>杠杆账户划转（margin.transfer)</p>
     * <p>借贷本金（margin.loan)</p>
     * <p>借贷计息（margin.interest)</p>
     * <p>归还借贷本金利息(margin.repay)</p>
     * <p>其他资产变化(other)</p>
     */
    private String event;
    private List<Asset> list;
}
