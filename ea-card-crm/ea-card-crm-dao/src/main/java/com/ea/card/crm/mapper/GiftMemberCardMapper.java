package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.common.StringLongEntry;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface GiftMemberCardMapper extends LmtechBaseMapper<GiftMemberCard> {
    List<GiftMemberCard> list(GiftMemberCard param);
    GiftMemberCard selectByCardId(@RequestParam String id);
    List<StringLongEntry> selectNoPresentCardOfOrder(List<String> orderNos);
    List<GiftMemberCard> selectByCardIds(List<String> ids);

    List<GiftMemberCard> selectByOrderAndStatus(@Param("orderNo") String orderNo, @Param("status") int status);

    long countNotPresentCards(@Param("userId") String userId);

    long countWaitReceiveCards(List<String> giftCardIds);

    List<GiftMemberCard> getAllMerber();
    int insertGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList);
    int updataGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList);
    int deleteGiftMemberCardBatchLogic(List<GiftMemberCard> giftMemberCardList);
    int updataGiftMemberCardStatus(GiftMemberCard giftMemberCard);
    int isExitsCard(String openId);
    List<GiftMemberCard> getGiftMemberCardInfo(Map<String,Object> map);
    int exitsCardNum(Map<String,Object> map);
    Map<String,String> getSpareCard(Map<String,Object> map);

    int updataBatch(List<GiftMemberCard> giftMemberCardList);

    int updataStatusByStatusAndOrderNo(Map<String, Object> map);
}
