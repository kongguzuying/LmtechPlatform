package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.CardActiveRecordDao;
import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.service.CardActiveRecordService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardActiveRecordServiceImpl extends AbstractDbManagerBaseImpl<CardActiveRecord> implements CardActiveRecordService {

    @Autowired
    private CardActiveRecordDao cardActiveRecordDao;

    @Override
    public Dao getDao() {
        return cardActiveRecordDao;
    }

    @Override
    public boolean checkPhoneAndOpenid(String phone, String uniqueId) {
        boolean exist = cardActiveRecordDao.existSuccessPhoneOrOpenId(phone, uniqueId);
        if (exist) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CardActiveRecord getAppliedRecordByUniqueId(String uniqueId) {
        CardActiveRecord record = cardActiveRecordDao.getByUniqueIdAndStatus(uniqueId, CardActiveRecord.STATUS_APPLIED);
        return record;
    }

    @Override
    public CardActiveRecord getApplyingRecordByUniqueId(String uniqueId) {
        CardActiveRecord record = cardActiveRecordDao.getByUniqueIdAndStatus(uniqueId, CardActiveRecord.STATUS_APPLYING);
        return record;
    }

    @Override
    public CardActiveRecord getAppliedRecordByOpenId(String openId) {
        CardActiveRecord record = cardActiveRecordDao.getByOpenIdAndStatus(openId, CardActiveRecord.STATUS_APPLIED);
        return record;
    }

    @Override
    public CardActiveRecord getAppliedRecordByOpenIds(List<String> openIds) {
        CardActiveRecord record = cardActiveRecordDao.getByOpenIdsAndStatus(openIds, CardActiveRecord.STATUS_APPLIED);
        return record;
    }

    @Override
    public void deleteActiveRecordByOpenId(String openId) {
        cardActiveRecordDao.deleteByOpenIdAndStatus(openId, CardActiveRecord.STATUS_APPLIED);
    }

    @Override
    public void deleteActiveRecordByUniqueId(String uniqueId) {
        cardActiveRecordDao.deleteByUniqueIdAndStatus(uniqueId, CardActiveRecord.STATUS_APPLIED);
    }

    @Override
    public void updateStatus(String id, int status) {
        cardActiveRecordDao.updateStatus(id, status);
    }
}
