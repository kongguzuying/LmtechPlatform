package com.ea.card.crm.service;

import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.service.DbManagerBase;

public interface RechargePayRecordService extends DbManagerBase<RechargePayRecord> {
    /**
     * 通过订单编号获取支付记录
     * @param orderNo
     * @return
     */
    RechargePayRecord getByOrderNo(String orderNo);

    /**
     * 设置订单状态为结束
     * @param orderNo
     */
    void orderFinished(String orderNo);
}
