package com.ea.card.crm.dao;

import com.ea.card.crm.model.CardPresentRecord;
import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.dao.Dao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CardPresentRecordDao extends Dao<CardPresentRecord> {
    boolean existPresentingRecord(String orderNo);

    List<String> getPresentingOrders(List<String> orderNos);

    List<CardPresentRecord> getByOrders(List<String> orderNos);


    CardPresentRecord getByOrder(String orderNo);

    void updateStatus(String id, int status);

    void updateOvertimeStatus(List<CardPresentRecord> records);

    void updateFinishStatus(List<CardPresentRecord> records);

    void updateDeleteStatus(String id, boolean isDelete);

    List<CardPresentRecord> getOvertimeRecordIds(Date overTime);

    List<CardPresentRecord> selectByGiftCardIdAndStatus(String giftCardId, int status);
}
