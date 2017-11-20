package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.CardActiveRecordDao;
import com.ea.card.crm.mapper.CardActiveRecordMapper;
import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardActiveRecordDaoImpl extends MyBatisDaoBase<CardActiveRecordMapper, CardActiveRecord> implements CardActiveRecordDao {
    @Override
    public boolean existSuccessPhoneOrOpenId(String phone, String uniqueId) {
        long count = baseMapper.countSuccessPhoneOrOpenId(phone, uniqueId);
        return count > 0;
    }

    @Override
    public CardActiveRecord getByUniqueId(String uniqueId) {
        return baseMapper.selectOneByUniqueId(uniqueId);
    }

    @Override
    public CardActiveRecord getByUniqueIdAndStatus(String uniqueId, int status) {
        return baseMapper.selectOneByUniqueIdAndStatus(uniqueId, status);
    }

    @Override
    public CardActiveRecord getByOpenIdAndStatus(String openId, int status) {
        return baseMapper.selectOneByOpenIdAndStatus(openId, status);
    }

    @Override
    public CardActiveRecord getByOpenIdsAndStatus(List<String> openIds, int status) {
        return baseMapper.selectOneByOpenIdsAndStatus(openIds, status);
    }

    @Override
    public void deleteByOpenIdAndStatus(String openId, int status) {
        baseMapper.deleteByOpenIdAndStatus(openId, status);
    }

    @Override
    public void deleteByCodeAndStatus(String code, int status) {
        baseMapper.deleteByCodeAndStatus(code, status);
    }

    @Override
    public void deleteByUniqueIdAndStatus(String uniqueId, int status) {
        baseMapper.deleteByUniqueIdAndStatus(uniqueId, status);
    }

    @Override
    public void updateStatus(String id, int status) {
        baseMapper.updateStatus(id, status);
    }
}
