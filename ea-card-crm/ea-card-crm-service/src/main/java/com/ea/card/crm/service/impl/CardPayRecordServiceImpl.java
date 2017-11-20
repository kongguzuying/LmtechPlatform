package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.CardPayRecordDao;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.service.CardPayRecordService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardPayRecordServiceImpl extends AbstractDbManagerBaseImpl<CardPayRecord> implements CardPayRecordService {

    @Autowired
    private CardPayRecordDao cardPayRecordDao;

    @Override
    public Dao getDao() {
        return cardPayRecordDao;
    }

    @Override
    public CardPayRecord getByOrderNo(String orderNo) {
        return cardPayRecordDao.getByOrderNo(orderNo);
    }

    @Override
    public void orderFinished(String orderNo) {
        cardPayRecordDao.updateStatusByOrderNo(orderNo, CardPayRecord.STATUS_FINISHED);
    }

    @Override
    public List<CardPayRecord> getByUserIdAndStatusAndSource(String userId, int status, int source) {
        return cardPayRecordDao.getByUserIdAndStatusAndSource(userId, status, source);
    }

}
