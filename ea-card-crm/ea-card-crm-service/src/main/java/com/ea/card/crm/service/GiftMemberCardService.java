package com.ea.card.crm.service;

import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.service.DbManagerBase;

import java.util.List;
import java.util.Map;

/**
 * 礼品会员卡服务
 *
 * @author
 */
public interface GiftMemberCardService extends DbManagerBase<GiftMemberCard> {
    /**
     * 通过订单号生成礼品会员卡
     *
     * @param orderNo
     */
    void genGiftCardOfOrder(String orderNo);

    /**
     * 通过id获取礼品会员卡
     *
     * @param id
     * @return
     */
    GiftMemberCard getById(String id);

    /**
     * 通过ids获取礼品会员卡
     *
     * @param ids
     * @return
     */
    List<GiftMemberCard> getByIds(List<String> ids);

    /**
     * 通过ids获取礼品会员卡
     *
     * @param orderNo
     * @param status
     * @return
     */
    List<GiftMemberCard> selectByOrderAndStatus(String orderNo, int status);

    /**
     * 从订单中领取会员卡
     *
     * @param presentRecordId
     * @return
     */
    GiftMemberCard receiveCardFromPresent(String presentRecordId, String openId);

    /**
     * 从订单中领取会员卡回退
     *
     * @param presentRecordId
     * @param giftMemberCard
     */
    void receiveCardFromPresentCallBack(String presentRecordId, GiftMemberCard giftMemberCard);

    /**
     * 获取订单内的礼品卡
     *
     * @param orderNo
     * @return
     */
    List<GiftMemberCard> getCardOfOrder(String orderNo);

    /**
     * 获取订单列表内未赠送的礼品卡数
     *
     * @param orderNos
     * @return
     */
    Map<String, Long> getNoPresentCountOfOrder(List<String> orderNos);

    /**
     * 获取订单列表内未赠送的礼品卡
     *
     * @param orderNo
     * @return
     */
    List<GiftMemberCard> getNoPresentCardOfOrder(String orderNo);

    /**
     * 是否存在未赠送的卡片
     *
     * @param userId
     * @return
     */
    boolean existNotPresentCards(String userId);

    /**
     * 是否存在待领取的卡片
     *
     * @param giftCardIds
     * @return
     */
    boolean existWaitReceiveCards(List<String> giftCardIds);

    /**
     * 获取活动状态可赠送的礼品卡
     *
     * @param userId
     * @return
     */
    List<GiftMemberCard> getActivePresentCard(String userId);

    /**
     * 获取内部送的vpass卡
     *
     * @param userId
     * @return
     */
    List<GiftMemberCard> getInnerCard(String userId);

    List<GiftMemberCard> getAllMerber();

    int insertGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList);

    int updataGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList);

    int deleteGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList);

    int updataGiftMemberCardStatus(GiftMemberCard giftMemberCard);

    int isExitsCard(String openId);

    List<GiftMemberCard> getGiftMemberCardInfo(Map<String, Object> map);

    int exitsCardNum(Map<String, Object> map);

    Map<String, String> getSpareCard(Map<String, Object> map);

    /**
     * 根据id批量更新
     *
     * @param giftMemberCardList
     * @return
     */
    int updateBatch(List<GiftMemberCard> giftMemberCardList);

    List<GiftMemberCard> getUserLockCardList(String openId);

    void unlockByList(String openId);

    Map<String, Integer> haveStatusNewCard(String presentRecordId, String openId);

    /**
     * 超时解锁礼品卡
     * @param orderNoList
     * @return
     */
    int overTimeUnlock(List orderNoList);
}
