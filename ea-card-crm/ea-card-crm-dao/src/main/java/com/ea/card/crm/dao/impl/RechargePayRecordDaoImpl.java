package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.RechargePayRecordDao;
import com.ea.card.crm.mapper.RechargePayRecordMapper;
import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

@Service
public class RechargePayRecordDaoImpl extends MyBatisDaoBase<RechargePayRecordMapper, RechargePayRecord> implements RechargePayRecordDao {
    @Override
    public RechargePayRecord getByOrderNo(String orderNo) {
        return baseMapper.selectByOrderNo(orderNo);
    }

    @Override
    public void updateStatusByOrderNo(String orderNo, int status) {

    }
}
