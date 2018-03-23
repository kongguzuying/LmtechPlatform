package com.ea.card.crm.dao.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ea.card.crm.dao.CardPayRecordDao;
import com.ea.card.crm.dao.RechargePayRecordDao;
import com.ea.card.crm.mapper.CardPayRecordMapper;
import com.ea.card.crm.mapper.RechargePayRecordMapper;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardPayRecordDaoImpl extends MyBatisDaoBase<CardPayRecordMapper, CardPayRecord> implements CardPayRecordDao {
    @Override
    public CardPayRecord getByOrderNo(String orderNo) {
        return baseMapper.selectByOrderNo(orderNo);
    }

    @Override
    public void updateStatusByOrderNo(String orderNo, int status) {
        CardPayRecord cardPayRecord = new CardPayRecord();
        cardPayRecord.setOrderNo(orderNo);
        cardPayRecord.setStatus(status);
        cardPayRecord.setUpdateDate(new Date());
        cardPayRecord.setPayDate(new Date());
        baseMapper.updateStatusByOrderNo(cardPayRecord);
    }

    @Override
    public boolean existNotPresentCards(String userId) {
        return false;
    }

    @Override
    public List<CardPayRecord> getByUserIdAndStatusAndSource(String userId, int status, int source) {
        EntityWrapper<CardPayRecord> ew = new EntityWrapper();
        ew.eq("user_id", userId).eq("status", status).eq("source", source);
        return baseMapper.selectList(ew);
    }

}
