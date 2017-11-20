package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.CardPresentRecordDao;
import com.ea.card.crm.mapper.CardPresentRecordMapper;
import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardPresentRecordDaoImpl extends MyBatisDaoBase<CardPresentRecordMapper, CardPresentRecord> implements CardPresentRecordDao {
    @Override
    public boolean existPresentingRecord(String orderNo) {
        CardPresentRecord param = new CardPresentRecord();
        param.setOrderNo(orderNo);
        param.setStatus(CardPresentRecord.STATUS_NEW);
        long count = baseMapper.countList(param);
        return count > 0;
    }

    @Override
    public List<String> getPresentingOrders(List<String> orderNos) {
        return baseMapper.selectPresentingOrders(orderNos);
    }

    @Override
    public List<CardPresentRecord> getByOrders(List<String> orderNos) {
        return baseMapper.selectByOrders(orderNos);
    }

    @Override
    public CardPresentRecord getByOrder(String orderNo) {
        return baseMapper.selectByOrder(orderNo);
    }


    @Override
    public void updateStatus(String id, int status) {
        CardPresentRecord cardPresentRecord = new CardPresentRecord();
        cardPresentRecord.setId(id);
        cardPresentRecord.setStatus(status);
        cardPresentRecord.setUpdateDate(new Date());
        baseMapper.updateStatus(cardPresentRecord);
    }

    @Override
    public void updateOvertimeStatus(List<CardPresentRecord> records) {
        if (!CollectionUtil.isNullOrEmpty(records)) {
            baseMapper.updateOvertimeStatus(records);
        }
    }

    @Override
    public void updateFinishStatus(List<CardPresentRecord> records) {
        if (!CollectionUtil.isNullOrEmpty(records)) {
            baseMapper.updateFinishStatus(records);
        }
    }

    @Override
    public void updateDeleteStatus(String id, boolean isDelete) {
        CardPresentRecord cardPresentRecord = new CardPresentRecord();
        cardPresentRecord.setId(id);
        cardPresentRecord.setDelete(isDelete);
        cardPresentRecord.setUpdateDate(new Date());
        baseMapper.updateDeleteStatus(cardPresentRecord);
    }

    @Override
    public List<CardPresentRecord> getOvertimeRecordIds(Date overTime) {
        return baseMapper.selectOvertimeRecordIds(overTime);
    }

    @Override
    public List<CardPresentRecord> selectByGiftCardIdAndStatus(String giftCardId, int status) {
        return baseMapper.selectByGiftCardIdAndStatus(giftCardId, status);
    }
}
