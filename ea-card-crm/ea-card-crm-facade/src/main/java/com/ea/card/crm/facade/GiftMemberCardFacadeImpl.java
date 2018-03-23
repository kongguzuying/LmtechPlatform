package com.ea.card.crm.facade;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.dao.CardPresentRecordDao;
import com.ea.card.crm.dao.GiftMemberCardDao;
import com.ea.card.crm.facade.request.*;
import com.ea.card.crm.facade.response.PayHistoryData;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.model.CardPresentRecord;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.*;
import com.lmtech.common.ExeResult;
import com.lmtech.common.PageData;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.service.IdWorker;
import com.lmtech.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(description = "礼品会员卡接口")
@RestController
@RequestMapping(value = "/giftcard")
public class GiftMemberCardFacadeImpl {

    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private CardPayRecordService cardPayRecordService;
    @Autowired
    private CardPresentRecordService cardPresentRecordService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;


    @ApiOperation(value = "获取大礼包页面数据")
    @RequestMapping(value = "/getGiftPageData", method = RequestMethod.GET)
    public StateResult getGiftPageData(@RequestParam String openId) {
        ExeResult result = new ExeResult();
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        if (register != null) {
            List<GiftMemberCard> giftCards = giftMemberCardService.getActivePresentCard(register.getUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("giftCards", giftCards);

            result.setSuccess(true);
            result.setData(data);
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取赠送卡片赠卡方页面数据", notes = "卡片状态，1-新建,3-已被领取,4-已被激活,5-不可用")
    @RequestMapping(value = "/getPresentCardPageData", method = RequestMethod.POST)
    public StateResult getPresentCardPageData(@RequestBody CardReceivePageRequest request) {
        ExeResult result = new ExeResult();

        if (StringUtil.isNullOrEmpty(request.getPresentRecordId())) {
            throw new IllegalArgumentException("presentRecordId卡片赠送记录编号不允许为空");
        }

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            CardPresentRecord presentRecord = cardPresentRecordService.get(request.getPresentRecordId());
            if (presentRecord != null) {
                Map<String, Object> data = new HashMap<>();
                //购买赠卡
                List<String> giftCardIds = StringUtil.splitString(presentRecord.getGiftCardIds());
                List<GiftMemberCard> cards = giftMemberCardService.getByIds(giftCardIds);
                data.put("giftCards", cards);
                data.put("cardBackground", cards.get(0).getCardBackground());
                data.put("cardLevelName", cards.get(0).getCardLevelName());
                data.put("overTimeNumber", presentRecord.getOverTimeNumber());
                data.put("totalAmount", presentRecord.getTotalAmount());
                data.put("totalNumber", presentRecord.getTotalNumber());
                data.put("shareNickname", register.getNickname());
                data.put("shareHeadimgurl", register.getHeadimgurl());
                result.setSuccess(true);
                result.setData(data);
            } else {
                throw new NotExistCardPresentRecordException();
            }
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取赠送卡片领卡方页面数据")
    @RequestMapping(value = "/getReceiveCardPageData", method = RequestMethod.POST)
    public StateResult getReceiveCardPageData(@RequestBody CardReceivePageRequest request) {
        ExeResult result = new ExeResult();

        if (StringUtil.isNullOrEmpty(request.getPresentRecordId())) {
            throw new IllegalArgumentException("presentRecordId卡片赠送记录编号不允许为空");
        }

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());

        CardPresentRecord presentRecord = cardPresentRecordService.get(request.getPresentRecordId());

        if (presentRecord == null) {
            throw new NotExistNoPresentCardException();
        }

        List<String> giftCardIds = StringUtil.splitString(presentRecord.getGiftCardIds());
        List<GiftMemberCard> cards = giftMemberCardService.getByIds(giftCardIds);
        Map<String, Object> data = new HashMap<>();

        data.put("giftCards", cards);
        data.put("cardBackground", cards.get(0).getCardBackground());
        data.put("cardLevelName", cards.get(0).getCardLevelName());
        data.put("overTimeNumber", presentRecord.getOverTimeNumber());
        data.put("totalAmount", presentRecord.getTotalAmount());
        data.put("totalNumber", presentRecord.getTotalNumber());
        data.put("belongPresent", '0');
        String shareOpenId = (!CollectionUtil.isNullOrEmpty(cards) ? cards.get(0).getOpenId() : null);

        if (register != null) {
            data.put("code", register.getCode());
            data.put("cardId", register.getCardId());

            if (shareOpenId.equalsIgnoreCase(register.getOpenId())) {
                data.put("shareNickname", register.getNickname());
                data.put("shareHeadimgurl", register.getHeadimgurl());
            } else {
                MemberRegister shareRegister = memberRegisterService.getNewByOpenId(shareOpenId);
                data.put("shareNickname", shareRegister.getNickname());
                data.put("shareHeadimgurl", shareRegister.getHeadimgurl());
            }
        } else {
            data.put("code", "");
            data.put("cardId", "");
            MemberRegister shareRegister = memberRegisterService.getNewByOpenId(shareOpenId);
            data.put("shareNickname", shareRegister.getNickname());
            data.put("shareHeadimgurl", shareRegister.getHeadimgurl());
        }

        for (GiftMemberCard card : cards) {
            if (request.getOpenId().equalsIgnoreCase(card.getRecvOpenId())) {
                data.put("belongPresent", '1');
                break;
            }
        }

        Map<String, Integer> map = giftMemberCardService.haveStatusNewCard(request.getPresentRecordId(), request.getOpenId());
        data.put("presentStatus", map.get("status").toString());

        /*boolean recvCard = false;
        for (GiftMemberCard card : cards) {
            shareOpenId = card.getOpenId();
            if (request.getOpenId().equalsIgnoreCase(card.getRecvOpenId())) {
                recvCard = true;
                break;
            }
        }
        if (!recvCard) {
            //不是该用户领取的无权限查看
            throw new NoPermissionViewException();
        }*/
//            if (!StringUtil.isNullOrEmpty(shareOpenId)) {
//                if (shareOpenId.equalsIgnoreCase(register.getOpenId())) {
//                    data.put("shareNickname", register.getNickname());
//                    data.put("shareHeadimgurl", register.getHeadimgurl());
//                } else {
//                    MemberRegister shareRegister = memberRegisterService.getByOpenId(shareOpenId);
//                    data.put("shareNickname", shareRegister.getNickname());
//                    data.put("shareHeadimgurl", shareRegister.getHeadimgurl());
//                }
//            }
        result.setSuccess(true);
        result.setData(data);

        return result.getResult();
    }

    @ApiOperation(value = "查看礼品卡购买历史", notes = "presentStatus:1-未赠送,2-赠送中,3-已赠送")
    @RequestMapping(value = "/getPayHistory", method = RequestMethod.POST)
    public StateResult getPayHistory(@RequestBody PayHistoryRequest request) {
        ExeResult result = new ExeResult();
        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            CardPayRecord param = new CardPayRecord();
            param.setStatus(CardPayRecord.STATUS_FINISHED);
            param.setUserId(register.getUserId());
            param.setSource(CardPayRecord.SOURCE_CARDPAY);
            PageData pageData = cardPayRecordService.getDataListOfPage(param, request.getPageIndex(), request.getPageSize());

            PayHistoryData payHistoryData = new PayHistoryData();
            payHistoryData.setPageIndex(pageData.getCurrentPageNumber());
            payHistoryData.setPageSize(pageData.getPageSize());
            payHistoryData.setTotal(pageData.getTotal());

            List<PayHistoryData.PayDataItem> payDataItems = new ArrayList<>();
            if (pageData.getTotal() > 0) {
                List<String> orderNos = new ArrayList<>();
                for (Object item : pageData.getItems()) {
                    CardPayRecord payRecord = (CardPayRecord) item;
                    GiftCardPayRequest payRequest = (GiftCardPayRequest) JsonUtil.fromJson(payRecord.getGiftRecord(), GiftCardPayRequest.class);
                    if (payRequest == null) {
                        continue;
                    }
                    orderNos.add(payRecord.getOrderNo());

                    PayHistoryData.PayDataItem payDataItem = new PayHistoryData.PayDataItem();
                    payDataItem.setmLevel(payRequest.getCardLevel());
                    payDataItem.setTotalAmount(payRequest.getTotalAmount());
                    payDataItem.setCardBackground(payRequest.getCardBackground());
                    payDataItem.setmLevelName(codeAdaptorService.getNameByCodeItemValue(String.valueOf(payRequest.getCardLevel())));
                    payDataItem.setGiftCardPayDetails(payRequest.getGiftCardPayDetails());
                    payDataItem.setOrderNo(payRecord.getOrderNo());
                    payDataItem.setPayDate(payRecord.getPayDate());
                    payDataItem.setPrePresentRecordId(IdWorkerUtil.generateStringId());
                    payDataItems.add(payDataItem);
                }

                for (PayHistoryData.PayDataItem payDataItem : payDataItems) {
                    int orderCardNums = 0;
                    for (GiftCardPayDetail giftCardPayDetail : payDataItem.getGiftCardPayDetails()) {
                        orderCardNums = orderCardNums + giftCardPayDetail.getNumber();
                    }
                    CardPresentRecord cardPresentRecord = cardPresentRecordService.getByOrder(payDataItem.getOrderNo());

                    if (cardPresentRecord == null) {
                        //no
//                            List<GiftMemberCard> giftMemberCardList = giftMemberCardService.selectByOrderAndStatus(payDataItem.getOrderNo(), GiftMemberCard.STATUS_NEW);
                        payDataItem.setPresentStatus(PayHistoryData.PayDataItem.PRESENT_STATUS_NONE);
                        payDataItem.setWaitPresentCount(orderCardNums);
                        payDataItem.setFinishPresentCount(0);
                    } else if (cardPresentRecord.getStatus() == 3) {
                        //overtime
                        payDataItem.setPresentStatus(PayHistoryData.PayDataItem.PRESENT_STATUS_OVERTIME);
                        List<GiftMemberCard> giftMemberCardList = giftMemberCardService.selectByOrderAndStatus(payDataItem.getOrderNo(), GiftMemberCard.STATUS_RECEIVED);
                        payDataItem.setWaitPresentCount(orderCardNums - giftMemberCardList.size());
                        payDataItem.setFinishPresentCount(giftMemberCardList.size());
                    } else if (cardPresentRecord.getStatus() == 2) {
                        //finish
                        payDataItem.setPresentStatus(PayHistoryData.PayDataItem.PRESENT_STATUS_FINISH);
                        payDataItem.setWaitPresentCount(0);
                        payDataItem.setFinishPresentCount(orderCardNums);
                    } else if (cardPresentRecord.getStatus() == 1) {
                        //ing
                        payDataItem.setPresentStatus(PayHistoryData.PayDataItem.PRESENT_STATUS_ING);
                        List<GiftMemberCard> giftMemberCardList = giftMemberCardService.selectByOrderAndStatus(payDataItem.getOrderNo(), GiftMemberCard.STATUS_RECEIVED);
                        payDataItem.setWaitPresentCount(orderCardNums - giftMemberCardList.size());
                        payDataItem.setFinishPresentCount(giftMemberCardList.size());
                    }
                }

                //过滤未赠送或已赠送的订单
                List<PayHistoryData.PayDataItem> filterPayDataItems = null;
                if (request.getNotPresent() != null) {
                    filterPayDataItems = new ArrayList<>();
                    //过滤已赠送，得到未赠送的订单列表
                    for (PayHistoryData.PayDataItem payDataItem : payDataItems) {
                        if (request.getNotPresent()) {
                            if (payDataItem.getPresentStatus() == PayHistoryData.PayDataItem.PRESENT_STATUS_NONE || payDataItem.getPresentStatus() == PayHistoryData.PayDataItem.PRESENT_STATUS_OVERTIME) {
                                filterPayDataItems.add(payDataItem);
                            }
                        } else {
                            //过滤未赠送，得到已赠送的订单列表
                            if (payDataItem.getPresentStatus() == PayHistoryData.PayDataItem.PRESENT_STATUS_FINISH) {
                                filterPayDataItems.add(payDataItem);
                            }
                        }
                    }
                }

                if (filterPayDataItems != null) {
                    payHistoryData.setDataItems(filterPayDataItems);
                } else {
                    payHistoryData.setDataItems(payDataItems);
                }
            }

            result.setSuccess(true);
            result.setData(payHistoryData);
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }

    @ApiOperation(value = "生成赠送卡片批次记录")
    @RequestMapping(value = "/genPresentCardRecord", method = RequestMethod.POST)
        public StateResult genPresentCardRecord(@RequestBody PresentGiftCardRequest request) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(request.getOrderNo()) && CollectionUtil.isNullOrEmpty(request.getGiftCardIds())) {
            throw new IllegalArgumentException("订单编号或礼品卡ids不允许为空");
        }

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register == null) {
            throw new NoneRegisterException();
        }

        if (!StringUtil.isNullOrEmpty(request.getOrderNo())) {
            //从订单生成赠送批次
            if (cardPresentRecordService.existPresentingRecord(request.getOrderNo())) {
                throw new CardPresentingException();
            }

            CardPayRecord cardPayRecord = cardPayRecordService.getByOrderNo(request.getOrderNo());
            if (!cardPayRecord.getUserId().equalsIgnoreCase(register.getUserId())) {
                throw new UserCheckException();
            }

            List<GiftMemberCard> noPresentCards = giftMemberCardService.getNoPresentCardOfOrder(request.getOrderNo());
            buildCardPresentRecord(result, request.getOrderNo(), register, noPresentCards, request.getPreId(), request.isActive());
        } else {
            //从大礼包中生成订单批次
            List<GiftMemberCard> giftMemberCards = giftMemberCardService.getByIds(request.getGiftCardIds());
            buildCardPresentRecord(result, request.getOrderNo(), register, giftMemberCards, request.getPreId(), request.isActive());
        }
        return result.getResult();
    }

    @ApiOperation(value = "激活赠送卡片批次记录")
    @RequestMapping(value = "/activePresentCardRecord", method = RequestMethod.GET)
    public StateResult activePresentCardRecord(@RequestParam String presentRecordId) {
        ExeResult result = new ExeResult();
        cardPresentRecordService.activePresentCardRecord(presentRecordId);
        result.setSuccess(true);
        return result.getResult();
    }

    @ApiOperation(value = "获取全部会员信息", hidden = true)
    @RequestMapping(value = "/getAllMerber", method = RequestMethod.GET)
    public StateResult getAllMerber() {
        ExeResult result = new ExeResult();
        List<GiftMemberCard> merberList = giftMemberCardService.getAllMerber();
        result.setSuccess(true);
        result.setData(merberList);
        return result.getResult();
    }

    @ApiOperation(value = "批量添加用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public StateResult insertGiftMemberCardBatch() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        for (int i = 0; i < 3; i++) {
            GiftMemberCard gift = new GiftMemberCard();
            gift.setId(IdWorkerUtil.generateStringId());
            gift.setCreateUser("zhengzj");
            gift.setCreateUserName("zhengzj");
            gift.setUpdateUser("zhengzj");
            gift.setUpdateUserName("zhengzj");
            gift.setDataVersion(1);
            gift.setDelete(false);
            gift.setCardLevel(1);
            gift.setValidMonth(6);
            gift.setUserId("zhengzj");
            gift.setPhone("13923456789");
            gift.setOpenId(RandomUtil.genRandomNumberString(4));
            gift.setStatus(1);
            gift.setRecvUserId(RandomUtil.genRandomNumberString(4));
            gift.setRecvPhone(RandomUtil.genRandomNumberString(11));
            gift.setRecvOpenId(RandomUtil.genRandomNumberString(4));
            gift.setBalance(200);
            gift.setOrderNo(RandomUtil.genRandomNumberString(4));
            giftMemberCardList.add(gift);
        }
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.insertGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "单个添加用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public StateResult insertGiftMemberCard() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        GiftMemberCard gift = new GiftMemberCard();
        gift.setId(IdWorkerUtil.generateStringId());
        gift.setCreateUser("zhengzj");
        gift.setCreateUserName("zhengzj");
        gift.setUpdateUser("zhengzj");
        gift.setUpdateUserName("zhengzj");
        gift.setDataVersion(1);
        gift.setDelete(false);
        gift.setCardLevel(1);
        gift.setValidMonth(6);
        gift.setUserId("zhengzj");
        gift.setPhone("13923456789");
        gift.setOpenId(RandomUtil.genRandomNumberString(4));
        gift.setStatus(1);
        gift.setRecvUserId(RandomUtil.genRandomNumberString(4));
        gift.setRecvPhone(RandomUtil.genRandomNumberString(11));
        gift.setRecvOpenId(RandomUtil.genRandomNumberString(4));
        gift.setBalance(200);
        gift.setOrderNo(RandomUtil.genRandomNumberString(4));
        giftMemberCardList.add(gift);
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.insertGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "批量更新用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/batchUpdata", method = RequestMethod.POST)
    public StateResult updataGiftMemberCardBatch() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        List<String> ids = new ArrayList<String>();
        ids.add("1844924263");
        ids.add("8589217151");
        for (int i = 0; i < 2; i++) {
            GiftMemberCard gift = new GiftMemberCard();
            gift.setOpenId(ids.get(i));
            giftMemberCardList.add(gift);
        }
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.updataGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "单个更新用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/updata", method = RequestMethod.POST)
    public StateResult updataGiftMemberCard() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        GiftMemberCard gift = new GiftMemberCard();
        gift.setOpenId("4611141893");
        giftMemberCardList.add(gift);
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.updataGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "批量删除用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public StateResult deleteGiftMemberCardBatch() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        List<String> ids = new ArrayList<String>();
        ids.add("1844924263");
        ids.add("8589217151");
        for (int i = 0; i < 2; i++) {
            GiftMemberCard gift = new GiftMemberCard();
            gift.setOpenId(ids.get(i));
            giftMemberCardList.add(gift);
        }
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.deleteGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "单个删除用户礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public StateResult deleteGiftMemberCard() {
        List<GiftMemberCard> giftMemberCardList = new ArrayList<GiftMemberCard>();
        GiftMemberCard gift = new GiftMemberCard();
        gift.setOpenId("4611141893");
        giftMemberCardList.add(gift);
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.deleteGiftMemberCardBatch(giftMemberCardList) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "更新用户礼品会员卡状态", hidden = true)
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public StateResult updataGiftMemberCardStatus(GiftMemberCard giftMemberCard) {
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.updataGiftMemberCardStatus(giftMemberCard) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "是否存在礼品会员卡", hidden = true)
    @RequestMapping(value = "/isExitsCard", method = RequestMethod.GET)
    public StateResult isExitsCard(String openId) {
        boolean isSuccess = false;
        isSuccess = giftMemberCardService.isExitsCard(openId) > 0;
        return new ExeResult(isSuccess).getResult();
    }

    @ApiOperation(value = "查询用户礼品会员卡", hidden = true)
    @RequestMapping(value = "/getGiftMemberCardInfo", method = RequestMethod.GET)
    public StateResult getGiftMemberCardInfo(Map<String, Object> map) {
        ExeResult result = new ExeResult();
        List<GiftMemberCard> merberList = giftMemberCardService.getGiftMemberCardInfo(map);
        result.setSuccess(true);
        result.setData(merberList);
        return result.getResult();
    }

    @ApiOperation(value = "获取已存在的礼品会员卡张数", hidden = true)
    @RequestMapping(value = "/exitsCardNum", method = RequestMethod.GET)
    public StateResult exitsCardNum(Map<String, Object> map) {
        ExeResult result = new ExeResult();
        int count = giftMemberCardService.exitsCardNum(map);
        result.setSuccess(true);
        result.setData(count);
        return result.getResult();
    }

    @ApiOperation(value = "获取剩余礼品会员卡信息", hidden = true)
    @RequestMapping(value = "/getSpareCard", method = RequestMethod.GET)
    public StateResult getSpareCard(@RequestParam("openId") String openId, @RequestParam("status") String status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("status", status);
        ExeResult result = new ExeResult();
        result.setSuccess(true);
        result.setData(giftMemberCardService.getSpareCard(map));
        return result.getResult();
    }

    private void buildCardPresentRecord(ExeResult result, String orderNo, MemberRegister register, List<GiftMemberCard> giftMemberCards, String preId, boolean active) {
        if (giftMemberCards.size() > 0) {
            double totalAmount = 0;
            int i = 0, totalNumber = giftMemberCards.size();
            String giftCardIds = "";
            for (GiftMemberCard item : giftMemberCards) {
                totalAmount += item.getBalance();
                if (i > 0) {
                    giftCardIds += ",";
                }
                giftCardIds += item.getId();
                i++;
            }

            CardPresentRecord presentRecord = new CardPresentRecord();
            if (!StringUtil.isNullOrEmpty(preId)) {
                presentRecord.setId(preId);
            }
            presentRecord.setCardLevel(giftMemberCards.get(0).getCardLevel());
            presentRecord.setCardLevelName(giftMemberCards.get(0).getCardLevelName());
            presentRecord.setUserId(register.getUserId());
            presentRecord.setTotalAmount(totalAmount);
            presentRecord.setTotalNumber(totalNumber);
            presentRecord.setGiftCardIds(giftCardIds);
            presentRecord.setStatus(CardPresentRecord.STATUS_NEW);
            presentRecord.setOrderNo(orderNo);
            presentRecord.setPresentDate(new Date());
            presentRecord.setDelete(!active);
            cardPresentRecordService.add(presentRecord);

            result.setSuccess(true);
            Map<String, Object> data = new HashMap<>();
            data.put("presentRecordId", presentRecord.getId());
            result.setSuccess(true);
            result.setData(data);
        } else {
            throw new NotExistNoPresentCardException();
        }
    }

    @ApiOperation(value = " 获取批次是否有未领取卡")
    @RequestMapping(value = "/getPresentStatusNewCard", method = RequestMethod.POST)
    public StateResult getPresentStatusNewCard(@RequestBody CardReceivePageRequest request) {

        ExeResult result = new ExeResult();
        Map resultMap = giftMemberCardService.haveStatusNewCard(request.getPresentRecordId(), request.getOpenId());
//            Map<String, Object> data = new HashMap<>();
//            data.put();
        result.setSuccess(true);
        result.setData(resultMap);
        return result.getResult();
    }

}
