package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.CardPresentRecordDao;
import com.ea.card.crm.model.CardPresentRecord;
import com.ea.card.crm.service.CardPresentRecordService;
import com.ea.card.crm.service.GiftMemberCardService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.DateUtil;
import com.lmtech.util.LoggerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RefreshScope
public class CardPresentRecordServiceImpl extends AbstractDbManagerBaseImpl<CardPresentRecord> implements CardPresentRecordService {

    private int overTimeHours = 24;

    @Autowired
    private CardPresentRecordDao cardPresentRecordDao;
    @Autowired
    private GiftMemberCardService giftMemberCardService;

    @Override
    public Dao getDao() {
        return cardPresentRecordDao;
    }

    @Override
    public boolean existPresentingRecord(String orderNo) {
        return cardPresentRecordDao.existPresentingRecord(orderNo);
    }

    @Override
    public List<String> getPresentingOrders(List<String> orderNos) {
        List<String> data = cardPresentRecordDao.getPresentingOrders(orderNos);
        if (data == null) {
            return new ArrayList<>();
        } else {
            return data;
        }
    }

    @Override
    public CardPresentRecord getByOrder(String orderNo) {
        return cardPresentRecordDao.getByOrder(orderNo);
    }

    @Override
    public void updateStatus(String id, int status) {
        cardPresentRecordDao.updateStatus(id, status);
    }

    @Override
    public void setPresentOvertime() {
        int time = (-1 * overTimeHours);
        Date overTime = DateUtil.addHours(new Date(), time);
        List<CardPresentRecord> overtimeRecords = cardPresentRecordDao.getOvertimeRecordIds(overTime);
        if (!CollectionUtil.isNullOrEmpty(overtimeRecords)) {
            List<CardPresentRecord> overtimeRecordList = new ArrayList<>();
            List<CardPresentRecord> finishRecordList = new ArrayList<>();
            List<String> orderList = new ArrayList<>();

            for (CardPresentRecord overtimeRecord : overtimeRecords) {
                orderList.add(overtimeRecord.getOrderNo());
                overtimeRecord.setUpdateDate(new Date());
                if (overtimeRecord.getOverTimeNumber() > 0) {
                    overtimeRecord.setReceiveNumber(overtimeRecord.getTotalNumber() - overtimeRecord.getOverTimeNumber());
                    overtimeRecordList.add(overtimeRecord);
                } else {
                    overtimeRecord.setReceiveNumber(overtimeRecord.getTotalNumber());
                    finishRecordList.add(overtimeRecord);
                }
            }

            LoggerManager.debug("处理赠送批次超时数：" + overtimeRecordList.size() + "，赠送批次完成数：" + finishRecordList.size());
            cardPresentRecordDao.updateOvertimeStatus(overtimeRecordList);
            cardPresentRecordDao.updateFinishStatus(finishRecordList);
            giftMemberCardService.overTimeUnlock(orderList);
        }
    }

    @Override
    public void activePresentCardRecord(String id) {
        cardPresentRecordDao.updateDeleteStatus(id, false);
    }

    @Override
    public List<CardPresentRecord> selectByGiftCardIdAndStatus(String giftCardId, int status) {
        return cardPresentRecordDao.selectByGiftCardIdAndStatus(giftCardId, status);
    }
}
