package com.ea.card.crm.service.impl;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.dao.GiftMemberCardDao;
import com.ea.card.crm.facade.request.GiftCardPayDetail;
import com.ea.card.crm.facade.request.GiftCardPayRequest;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.model.CardPresentRecord;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.model.RechargePayRecord;
import com.ea.card.crm.service.CardPayRecordService;
import com.ea.card.crm.service.CardPresentRecordService;
import com.ea.card.crm.service.GiftMemberCardService;
import com.ea.card.crm.service.exception.*;
import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.facade.stub.CodeFacade;
import com.lmtech.redis.constants.RedisQueueStatus;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RefreshScope
public class GiftMemberCardServiceImpl extends AbstractDbManagerBaseImpl<GiftMemberCard> implements GiftMemberCardService {

    private long queueExpireTime = 108000;      //30小时

    @Autowired
    private GiftMemberCardDao giftMemberCardDao;
    @Autowired
    private CardPayRecordService cardPayRecordService;
    @Autowired
    private CardPresentRecordService cardPresentRecordService;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private RedisDataService redisDataService;

    @Override
    public Dao getDao() {
        return giftMemberCardDao;
    }

    @Autowired
    public CodeFacade codeFacade;

    @Override
    @Transactional
    public synchronized void genGiftCardOfOrder(String orderNo) {
        //TODO 校验订单状态
        //改变订单状态
        cardPayRecordService.orderFinished(orderNo);

        CardPayRecord payRecord = cardPayRecordService.getByOrderNo(orderNo);
        if (payRecord == null) {
            throw new GenGiftCardException(ErrorConstants.ERR_CRM_GEN_GIFT_CARD_RECORDNOTEXIST_MSG, ErrorConstants.ERR_CRM_GEN_GIFT_CARD_RECORDNOTEXIST);
        }
        if (payRecord.getStatus() == RechargePayRecord.STATUS_WAIT_PAY) {
            throw new GenGiftCardException(ErrorConstants.ERR_CRM_GEN_GIFT_CARD_NOPAY_MSG, ErrorConstants.ERR_CRM_GEN_GIFT_CARD_NOPAY);
        }
        if (payRecord.getStatus() == RechargePayRecord.STATUS_ERROR) {
            throw new GenGiftCardException(ErrorConstants.ERR_CRM_GEN_GIFT_CARD_PAYFAILED_MSG, ErrorConstants.ERR_CRM_GEN_GIFT_CARD_PAYFAILED);
        }

        String giftRecord = payRecord.getGiftRecord();
        GiftCardPayRequest request = (GiftCardPayRequest) JsonUtil.fromJson(giftRecord, GiftCardPayRequest.class);
        if (request == null || CollectionUtil.isNullOrEmpty(request.getGiftCardPayDetails())) {
            throw new NotExistGiftCardPayDetailException();
        }

        if (request.getCardLevel() == GiftMemberCard.LEVEL_VPASS) {
            List<GiftMemberCard> giftMemberCardList = giftMemberCardService.getInnerCard(payRecord.getUserId());
            List<GiftMemberCard> giftMemberCards = new ArrayList<>();
            if (giftMemberCardList != null && giftMemberCardList.size() != 0) {
                //使用vpass礼包买卡
                if (request.getTotalNumber() <= giftMemberCardList.size()) {
                    //购买vpass卡数小于等于礼包中的vpass卡数
                    int count = 0;
                    for (int i = 0; i < request.getGiftCardPayDetails().size(); i++) {
                        GiftCardPayDetail giftCardPayDetail = request.getGiftCardPayDetails().get(i);
                        for (int j = 0; j < giftCardPayDetail.getNumber(); j++) {
                            giftMemberCardList.get(count).setOrderNo(orderNo);
                            giftMemberCardList.get(count).setBalance(giftCardPayDetail.getPrice());
                            giftMemberCardList.get(count).setCardBackground(request.getCardBackground());

                            giftMemberCardList.get(count).setGiftCategoryId(giftCardPayDetail.getGiftCategoryId());
                            giftMemberCardList.get(count).setCardCategoryId(request.getCardChildCategoryId());
                            count = count + 1;
                        }
                    }
                } else {
                    //购买vpass卡数大于礼包中的vpass卡数
                    int count = 0;
                    int mLevel = request.getCardLevel();

                     for (GiftCardPayDetail record : request.getGiftCardPayDetails()) {
                        for (int i = 0; i < record.getNumber(); i++) {

                            if (count < giftMemberCardList.size()) {
                                giftMemberCardList.get(count).setOrderNo(orderNo);
                                giftMemberCardList.get(count).setBalance(record.getPrice());
                                giftMemberCardList.get(count).setCardBackground(request.getCardBackground());
                                giftMemberCardList.get(count).setGiftCategoryId(record.getGiftCategoryId());
                                giftMemberCardList.get(count).setCardCategoryId(request.getCardChildCategoryId());
                                count = count + 1;
                            } else {
                                GiftMemberCard giftMemberCard = new GiftMemberCard();
                                giftMemberCard.setSource(GiftMemberCard.SOURCE_PAY);
                                giftMemberCard.setCardLevel(mLevel);
                                giftMemberCard.setOrderNo(orderNo);
                                giftMemberCard.setUserId(payRecord.getUserId());
                                giftMemberCard.setPhone(payRecord.getPhone());
                                giftMemberCard.setValidMonth(12);
                                giftMemberCard.setStatus(GiftMemberCard.STATUS_NEW);
                                giftMemberCard.setBalance(record.getPrice());
                                giftMemberCard.setCardCategoryId(request.getCardChildCategoryId());
                                giftMemberCard.setGiftCategoryId(record.getGiftCategoryId());
                                giftMemberCard.setCardBackground(request.getCardBackground());
                                giftMemberCard.setOpenId(request.getOpenId());
                                giftMemberCard.setCreateDate(new Date());
                                giftMemberCard.setUpdateDate(new Date());
                                giftMemberCard.setDataVersion(1);
                                giftMemberCard.setId(IdWorkerUtil.generateStringId());
                                giftMemberCards.add(giftMemberCard);
                            }
                        }
                    }
                }
                giftMemberCardService.updateBatch(giftMemberCardList);
                if (giftMemberCards != null && giftMemberCards.size() != 0) {
                    insertGiftMemberCardBatch(giftMemberCards);
                }
                return;
            }
        }

        int mLevel = request.getCardLevel();
        for (GiftCardPayDetail record : request.getGiftCardPayDetails()) {
            List<GiftMemberCard> giftMemberCards = new ArrayList<>();
            for (int i = 0; i < record.getNumber(); i++) {
                GiftMemberCard giftMemberCard = new GiftMemberCard();
                giftMemberCard.setSource(GiftMemberCard.SOURCE_PAY);
                giftMemberCard.setCardLevel(mLevel);
                giftMemberCard.setOrderNo(orderNo);
                giftMemberCard.setUserId(payRecord.getUserId());
                giftMemberCard.setPhone(payRecord.getPhone());
                giftMemberCard.setValidMonth(12);
                giftMemberCard.setStatus(GiftMemberCard.STATUS_NEW);
                giftMemberCard.setBalance(record.getPrice());
                giftMemberCard.setCardCategoryId(request.getCardChildCategoryId());
                giftMemberCard.setGiftCategoryId(record.getGiftCategoryId());
                giftMemberCard.setCardBackground(request.getCardBackground());
                giftMemberCard.setOpenId(request.getOpenId());
                giftMemberCard.setCreateDate(new Date());
                giftMemberCard.setUpdateDate(new Date());
                giftMemberCard.setDataVersion(1);
                giftMemberCard.setId(IdWorkerUtil.generateStringId());

                giftMemberCards.add(giftMemberCard);
            }
            insertGiftMemberCardBatch(giftMemberCards);
        }
    }

    @Override
    public GiftMemberCard getById(String id) {
        return giftMemberCardDao.getById(id);
    }

    @Override
    public List<GiftMemberCard> getByIds(List<String> ids) {
        return giftMemberCardDao.getByIds(ids);
    }

    @Override
    public List<GiftMemberCard> selectByOrderAndStatus(String orderNo, int status) {
        return giftMemberCardDao.selectByOrderAndStatus(orderNo, status);
    }

    private Object lockObj = new Object();
    private String prefixRedisKey = "gift_present:";

    @Override
    public GiftMemberCard receiveCardFromPresent(String presentRecordId, String openId) {
        CardPresentRecord presentRecord = cardPresentRecordService.get(presentRecordId);

        if (presentRecord == null) {
            throw new NotExistCardPresentRecordException();
        }

        List<String> giftCardIds = StringUtil.splitString(presentRecord.getGiftCardIds());
        List<GiftMemberCard> giftMemberCards = giftMemberCardDao.getByIds(giftCardIds);

        if (!CollectionUtil.isNullOrEmpty(giftMemberCards)) {
            //优先获取被当前用户锁定的卡
            for (GiftMemberCard giftMemberCard : giftMemberCards) {
                if (giftMemberCard.getStatus() == GiftMemberCard.STATUS_RECVLOCK && giftMemberCard.getRecvOpenId().equals(openId)) {
                    return giftMemberCard;
                }
            }

            //从Redis中获取卡
            String key = prefixRedisKey + presentRecordId;
            String status = redisDataService.getQueueStatus(key);
            if (!StringUtil.isNullOrEmpty(status)) {
                //存在批次队列
                if (status.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_BUILDING)) {
                    //等待队列构建完毕
                    redisDataService.waitQueueBuildFinish(key);
                }
            } else {
                //不存批次队列，构建列表
                synchronized (lockObj) {
                    redisDataService.buildQueue(key, giftCardIds, queueExpireTime);
                }
            }
            String giftCardId = redisDataService.popQueue(key);
            if (!StringUtil.isNullOrEmpty(giftCardId)) {
                return giftMemberCardDao.getById(giftCardId);
            }
        } else {
            throw new NoneGiftCardInOrderException();
        }
        throw new NoCardForReceiveException();
    }

    @Override
    public void receiveCardFromPresentCallBack(String presentRecordId, GiftMemberCard giftMemberCard) {
        if (giftMemberCard != null) {
            giftMemberCard.setRecvOpenId(null);
            giftMemberCard.setStatus(GiftMemberCard.STATUS_NEW);
            giftMemberCard.setPhone(null);
            giftMemberCardService.edit(giftMemberCard);

            String key = prefixRedisKey + presentRecordId;
            redisDataService.pushQueue(key, giftMemberCard.getId());
        }
    }

    @Override
    public List<GiftMemberCard> getCardOfOrder(String orderNo) {
        return giftMemberCardDao.getByOrderNo(orderNo);
    }

    @Override
    public Map<String, Long> getNoPresentCountOfOrder(List<String> orderNos) {
        return giftMemberCardDao.getNoPresentCountOfOrder(orderNos);
    }

    @Override
    public List<GiftMemberCard> getNoPresentCardOfOrder(String orderNo) {
        return giftMemberCardDao.getNoPresentCardOfOrder(orderNo);
    }

    @Override
    public boolean existNotPresentCards(String userId) {

        List<CardPayRecord> cardPayRecordList = cardPayRecordService.getByUserIdAndStatusAndSource(userId, CardPayRecord.STATUS_FINISHED, CardPayRecord.SOURCE_CARDPAY);

        for (CardPayRecord cardPayRecord : cardPayRecordList) {
            CardPresentRecord cardPresentRecord = cardPresentRecordService.getByOrder(cardPayRecord.getOrderNo());
            if (cardPresentRecord == null || cardPresentRecord.getStatus() == 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existWaitReceiveCards(List<String> giftCardIds) {
        return giftMemberCardDao.existWaitReceiveCards(giftCardIds);
    }

    @Override
    public List<GiftMemberCard> getActivePresentCard(String userId) {
        return giftMemberCardDao.getInnerCard(userId);
    }

    @Override
    public List<GiftMemberCard> getInnerCard(String userId) {
        return giftMemberCardDao.getInnerCard(userId);
    }

    @Override
    public List<GiftMemberCard> getAllMerber() {
        return giftMemberCardDao.getAllMerber();
    }

    @Override
    public int insertGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        if (!CollectionUtil.isNullOrEmpty(giftMemberCardList)) {
            return giftMemberCardDao.insertGiftMemberCardBatch(giftMemberCardList);
        } else {
            return 0;
        }
    }

    @Override
    public int updataGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        return giftMemberCardDao.updataGiftMemberCardBatch(giftMemberCardList);
    }

    @Override
    public int deleteGiftMemberCardBatch(List<GiftMemberCard> giftMemberCardList) {
        return giftMemberCardDao.deleteGiftMemberCardBatch(giftMemberCardList);
    }

    @Override
    public int updataGiftMemberCardStatus(GiftMemberCard giftMemberCard) {
        return giftMemberCardDao.updataGiftMemberCardStatus(giftMemberCard);
    }

    @Override
    public int isExitsCard(String openId) {
        return giftMemberCardDao.isExitsCard(openId);
    }

    @Override
    public List<GiftMemberCard> getGiftMemberCardInfo(Map<String, Object> map) {
        return giftMemberCardDao.getGiftMemberCardInfo(map);
    }

    @Override
    public int exitsCardNum(Map<String, Object> map) {
        return giftMemberCardDao.exitsCardNum(map);
    }

    @Override
    public Map<String, String> getSpareCard(Map<String, Object> map) {
        return giftMemberCardDao.getSpareCard(map);
    }

    @Override
    public int updateBatch(List<GiftMemberCard> giftMemberCardList) {
        return giftMemberCardDao.updataBatch(giftMemberCardList);
    }

    @Override
    public List<GiftMemberCard> getUserLockCardList(String openId) {
        return giftMemberCardDao.getByOpenIdAndStatus(openId, GiftMemberCard.STATUS_RECVLOCK);
    }

    @Override
    public void unlockByList(String openId) {
        List<GiftMemberCard> giftMemberCardList = getUserLockCardList(openId);
        for (GiftMemberCard giftMemberCard : giftMemberCardList) {
            giftMemberCard.setRecvOpenId(null);
            giftMemberCard.setRecvDate(null);
            giftMemberCard.setRecvPhone(null);
            giftMemberCard.setRecvUserId(null);
            giftMemberCard.setStatus(GiftMemberCard.STATUS_RECEIVED);
        }
        if (!CollectionUtil.isNullOrEmpty(giftMemberCardList)) {
            giftMemberCardDao.updataBatch(giftMemberCardList);
        }
    }

    @Override
    public Map<String, Integer> haveStatusNewCard(String presentRecordId, String openId) {
        CardPresentRecord presentRecord = cardPresentRecordService.get(presentRecordId);
        if (presentRecord == null) {
            throw new NotExistCardPresentRecordException();
        }

        List<String> giftCardIds = StringUtil.splitString(presentRecord.getGiftCardIds());
        Map resultMap = new HashMap();

        if (presentRecord.getStatus() == CardPresentRecord.STATUS_OVERTIME) {
            //超时
            resultMap.put("status", CardPresentRecord.STATUS_OVERTIME);
        } else if (presentRecord.getStatus() == CardPresentRecord.STATUS_FINISH) {
            //已完成
            resultMap.put("status", CardPresentRecord.STATUS_FINISH);
        } else if (presentRecord.getStatus() == CardPresentRecord.STATUS_NEW) {
            List<GiftMemberCard> giftMemberCards = giftMemberCardDao.getByIds(giftCardIds);

            if (!CollectionUtil.isNullOrEmpty(giftMemberCards)) {
                //优先获取被当前用户锁定的卡
                for (GiftMemberCard giftMemberCard : giftMemberCards) {
                    if ((giftMemberCard.getStatus() == GiftMemberCard.STATUS_RECVLOCK && giftMemberCard.getRecvOpenId().equals(openId))
                            || giftMemberCard.getStatus() == GiftMemberCard.STATUS_NEW) {
                        resultMap.put("status", CardPresentRecord.STATUS_NEW);
                        return resultMap;
                    }
                }
            } else {
                throw new NoneGiftCardInOrderException();
            }

            //存在卡被锁定，返回已完成
            resultMap.put("status", CardPresentRecord.STATUS_FINISH);
        }
        return resultMap;
    }

    @Override
    public int overTimeUnlock(List orderNoList) {
        return giftMemberCardDao.updataStatusByStatusAndOrderNoList(orderNoList, GiftMemberCard.STATUS_RECVLOCK, GiftMemberCard.STATUS_NEW);
    }
}
