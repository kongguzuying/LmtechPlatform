package com.ea.card.crm.dao;

import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.dao.Dao;
import java.util.List;
import java.util.Map;

public interface GiftMemberCardDao extends Dao<GiftMemberCard> {
    /**
     * 获取订单下的礼品会员卡
     * @param orderNo
     * @return
     */
    List<GiftMemberCard> getByOrderNo(String orderNo);

    GiftMemberCard getById(String id);

    List<GiftMemberCard> getByIds(List<String> ids);

    List<GiftMemberCard> selectByOrderAndStatus(String orderNo, int status);

    Map<String, Long> getNoPresentCountOfOrder(List<String> orderNos);

    List<GiftMemberCard> getNoPresentCardOfOrder(String orderNo);

    boolean existNotPresentCards(String userId);

    boolean existWaitReceiveCards(List<String> giftCardIds);


    List<GiftMemberCard> getAllMerber();
    int insertGiftMemberCardBatch(List<GiftMemberCard > giftMemberCardList);
    int updataGiftMemberCardBatch(List<GiftMemberCard > giftMemberCardList);
    int deleteGiftMemberCardBatch(List<GiftMemberCard > giftMemberCardList);
    int updataGiftMemberCardStatus(GiftMemberCard giftMemberCard);
    int isExitsCard(String openId);
    List<GiftMemberCard> getGiftMemberCardInfo(Map<String,Object> map);
    int exitsCardNum(Map<String,Object> map);
    Map<String,String> getSpareCard(Map<String,Object> map);


    List<GiftMemberCard> getInnerCard(String userId);

    /**
     * 根据id批量更新
     * @param giftMemberCardList
     * @return
     */
    int updataBatch(List<GiftMemberCard> giftMemberCardList);

    List<GiftMemberCard> getByOpenIdAndStatus(String openId, int status);

    /**
     * 根据orderNo和status更新
     * @param orderNoList
     * @param sourceStatus
     * @param targetStatus
     * @return
     */
    int updataStatusByStatusAndOrderNoList(List orderNoList, int sourceStatus, int targetStatus);
}
