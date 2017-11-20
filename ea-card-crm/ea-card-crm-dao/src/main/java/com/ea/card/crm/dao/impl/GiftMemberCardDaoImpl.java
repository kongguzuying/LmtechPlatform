package com.ea.card.crm.dao.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ea.card.crm.dao.GiftMemberCardDao;
import com.ea.card.crm.mapper.GiftMemberCardMapper;
import com.ea.card.crm.model.GiftCategory;
import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.common.StringLongEntry;
import com.lmtech.common.StringLongEntryMap;
import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GiftMemberCardDaoImpl extends MyBatisDaoBase<GiftMemberCardMapper, GiftMemberCard> implements GiftMemberCardDao {



    @Override
    public List<GiftMemberCard> getByOrderNo(String orderNo) {
        GiftMemberCard param = new GiftMemberCard();
        param.setOrderNo(orderNo);
        return baseMapper.list(param);
    }

    @Override
    public GiftMemberCard getById(String id) {
        return baseMapper.selectByCardId(id);
    }

    @Override
    public List<GiftMemberCard> getByIds(List<String> ids) {
        if (!CollectionUtil.isNullOrEmpty(ids)) {
            return baseMapper.selectByCardIds(ids);
        } else {
            return new ArrayList<>();
        }
//        EntityWrapper<GiftMemberCard> ew = new EntityWrapper();
//        ew.in("id", ids);
//        return baseMapper.selectList(ew);
    }

    @Override
    public List<GiftMemberCard> selectByOrderAndStatus(String orderNo, int status) {
        return baseMapper.selectByOrderAndStatus(orderNo, status);
    }

    @Override
    public Map<String, Long> getNoPresentCountOfOrder(List<String> orderNos) {
        List<StringLongEntry> entrys = baseMapper.selectNoPresentCardOfOrder(orderNos);
        StringLongEntryMap map = new StringLongEntryMap(entrys);
        return map.getEntryMap();
    }

    @Override
    public List<GiftMemberCard> getNoPresentCardOfOrder(String orderNo) {
        GiftMemberCard param = new GiftMemberCard();
        //param.setSource(GiftMemberCard.SOURCE_PAY);
        param.setOrderNo(orderNo);
        param.setStatus(GiftMemberCard.STATUS_NEW);
        return baseMapper.list(param);
    }

    @Override
    public boolean existNotPresentCards(String userId) {
        long count = baseMapper.countNotPresentCards(userId);
        return count > 0;
    }

    @Override
    public boolean existWaitReceiveCards(List<String> giftCardIds) {
        long count = baseMapper.countWaitReceiveCards(giftCardIds);
        return count > 0;
    }

    @Override
    public List<GiftMemberCard> getAllMerber(){
        return baseMapper.getAllMerber();
    }

    @Override
    public int insertGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        return baseMapper.insertGiftMemberCardBatch(giftMemberCardList);
    }

    @Override
    public int updataGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        return baseMapper.updataGiftMemberCardBatch(giftMemberCardList);
    }

    @Override
    public int deleteGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        return baseMapper.deleteGiftMemberCardBatchLogic(giftMemberCardList);
    }

    @Override
    public int updataGiftMemberCardStatus(GiftMemberCard giftMemberCard){
        return baseMapper.updataGiftMemberCardStatus(giftMemberCard);
    }

    @Override
    public int isExitsCard(String openId){
        return baseMapper.isExitsCard(openId);
    }

    @Override
    public List<GiftMemberCard> getGiftMemberCardInfo(Map<String,Object> map){
        return baseMapper.getGiftMemberCardInfo(map);
    }

    @Override
    public int exitsCardNum(Map<String,Object> map){
        return baseMapper.exitsCardNum(map);
    }

    @Override
    public Map<String,String> getSpareCard(Map<String,Object> map){
        return baseMapper.getSpareCard(map);
    }

    @Override
    public List<GiftMemberCard> getInnerCard(String userId) {
        EntityWrapper<GiftMemberCard> ew = new EntityWrapper();
        ew.eq("source", GiftMemberCard.SOURCE_INNER_PRESENT).eq("status", GiftMemberCard.STATUS_NEW)
                .eq("user_id", userId).eq("card_level", GiftMemberCard.LEVEL_VPASS).isNull("order_no");
        return baseMapper.selectList(ew);
    }

    @Override
    public int updataBatch(List<GiftMemberCard> giftMemberCardList) {
        return baseMapper.updataBatch(giftMemberCardList);
    }

    @Override
    public List<GiftMemberCard> getByOpenIdAndStatus(String openId, int status) {
        EntityWrapper<GiftMemberCard> ew = new EntityWrapper();
        ew.eq("open_id", openId).eq("status", GiftMemberCard.STATUS_NEW);
        return baseMapper.selectList(ew);
    }

    @Override
    public int updataStatusByStatusAndOrderNoList(List orderNoList, int sourceStatus, int targetStatus) {
        Map map = new HashMap();
        map.put("list", orderNoList);
        map.put("sourceStatus", sourceStatus);
        map.put("targetStatus", targetStatus);
        return baseMapper.updataStatusByStatusAndOrderNo(map);
    }

}
