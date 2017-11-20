package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CardPresentRecordMapper extends LmtechBaseMapper<CardPresentRecord> {
    long countList(CardPresentRecord record);

    List<String> selectPresentingOrders(List<String> orderNos);

    List<CardPresentRecord> selectByOrders(List<String> orderNos);

    CardPresentRecord selectByOrder(@Param("orderNo") String orderNo);

    void updateStatus(CardPresentRecord cardPresentRecord);

    void updateDeleteStatus(CardPresentRecord cardPresentRecord);

    void updateOvertimeStatus(List<CardPresentRecord> records);

    void updateFinishStatus(List<CardPresentRecord> records);

    List<CardPresentRecord> selectOvertimeRecordIds(@Param("overTime") Date overTime);

    List<CardPresentRecord> selectByGiftCardIdAndStatus(@Param("giftCardId") String giftCardId, @Param("status") int status);
}
