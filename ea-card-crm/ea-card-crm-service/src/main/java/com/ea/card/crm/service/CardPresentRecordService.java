package com.ea.card.crm.service;

import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.service.DbManagerBase;

import java.util.List;

/**
 * 卡片赠送记录服务
 */
public interface CardPresentRecordService extends DbManagerBase<CardPresentRecord> {
    /**
     * 是否存在赠送中的卡片
     * @param orderNo
     * @return
     */
    boolean existPresentingRecord(String orderNo);

    /**
     * 获取赠送中的卡片订单
     * @param orderNos
     * @return
     */
    List<String> getPresentingOrders(List<String> orderNos);

    /**
     * 根据order获取赠送记录
     * @param orderNo
     * @return
     */
    CardPresentRecord getByOrder(String orderNo);

    /**
     * 更新赠送记录状态
     * @param id
     * @param status
     */
    void updateStatus(String id, int status);

    /**
     * 设置赠送超时
     */
    void setPresentOvertime();

    /**
     * 激活赠送记录
     * @param id
     */
    void activePresentCardRecord(String id);

    /**
     * 根据GiftCardId Status查询
     * @param giftCardId
     * @param status
     * @return
     */
    List<CardPresentRecord> selectByGiftCardIdAndStatus(String giftCardId, int status);
}
