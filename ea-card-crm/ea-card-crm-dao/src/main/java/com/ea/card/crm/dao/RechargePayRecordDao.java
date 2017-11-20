package com.ea.card.crm.dao;

import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.dao.Dao;

public interface RechargePayRecordDao extends Dao<RechargePayRecord> {
    RechargePayRecord getByOrderNo(String orderNo);

    void updateStatusByOrderNo(String orderNo, int status);
}
