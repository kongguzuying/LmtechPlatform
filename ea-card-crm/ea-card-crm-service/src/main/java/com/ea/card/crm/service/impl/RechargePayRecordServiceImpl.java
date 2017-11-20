package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.RechargePayRecordDao;
import com.ea.card.crm.model.RechargePayRecord;
import com.ea.card.crm.service.RechargePayRecordService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargePayRecordServiceImpl extends AbstractDbManagerBaseImpl<RechargePayRecord> implements RechargePayRecordService {

    @Autowired
    private RechargePayRecordDao rechargePayRecordDao;

    @Override
    public Dao getDao() {
        return rechargePayRecordDao;
    }

    @Override
    public RechargePayRecord getByOrderNo(String orderNo) {
        return rechargePayRecordDao.getByOrderNo(orderNo);
    }

    @Override
    public void orderFinished(String orderNo) {
        rechargePayRecordDao.updateStatusByOrderNo(orderNo, RechargePayRecord.STATUS_FINISHED);
    }


}
