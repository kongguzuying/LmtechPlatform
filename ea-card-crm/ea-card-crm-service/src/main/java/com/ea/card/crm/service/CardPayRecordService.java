package com.ea.card.crm.service;

import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.service.DbManagerBase;

import java.util.List;

public interface CardPayRecordService extends DbManagerBase<CardPayRecord> {
    /**
     * 通过订单编号获取支付记录
     * @param orderNo
     * @return
     */
    CardPayRecord getByOrderNo(String orderNo);

    /**
     * 设置订单状态为结束
     * @param orderNo
     */
    void orderFinished(String orderNo);

    /**
     * 通过userId获取支付记录
     * @param userId
     * @return
     */
    List<CardPayRecord> getByUserIdAndStatusAndSource(String userId, int status, int source);
}
