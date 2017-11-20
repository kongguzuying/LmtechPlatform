package com.ea.card.crm.dao;

import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.dao.Dao;

import java.util.List;

public interface CardPayRecordDao extends Dao<CardPayRecord> {
    CardPayRecord getByOrderNo(String orderNo);

    void updateStatusByOrderNo(String orderNo, int status);

    boolean existNotPresentCards(String userId);

    List<CardPayRecord> getByUserIdAndStatusAndSource(String userId, int status, int source);
}
