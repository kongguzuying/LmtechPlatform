package com.ea.card.crm.service.impl;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.facade.request.GiftCardPayDetail;
import com.ea.card.crm.facade.request.GiftCardPayRequest;
import com.ea.card.crm.facade.response.RechargeListResult;
import com.ea.card.crm.model.*;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.NotExistOrderException;
import com.ea.card.crm.service.exception.RechargePayException;
import com.ea.card.crm.service.exception.RechargeRequestException;
import com.ea.card.crm.service.exception.RemoteDateErrorException;
import com.ea.card.crm.service.util.WxUtil;
import com.ea.card.crm.service.vo.BalanceLockResult;
import com.ea.card.crm.service.vo.RechargePayResult;
import com.ea.card.crm.service.vo.RechargeReqResult;
import com.lmtech.common.ContextManager;
import com.lmtech.common.StateResult;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@RefreshScope
public class PaymentServiceImpl implements PaymentService {
	@Value("${service.url_recharge_request}")
	private String URL_RECHARGE_REQUEST;
	
	@Value("${service.url_recharge_payment}")
    private String URL_RECHARGE_PAYMENT;
	
	@Value("${service.url_recharge_applet_payment}")
    private String URL_RECHARGE_APPLET_PAYMENT;

	@Value("${service.url_account_balance}")
    private String URL_ACCOUNT_BALANCE;
	
	@Value("${service.url_account_balance_list}")
    private String URL_ACCOUNT_BALANCE_LIST;
	
	@Value("${service.url_account_recorddetail}")
    private String URL_ACCOUNT_RECORDDETAIL;
	
	@Value("${service.url_account_addwxrechargemoney}")
    private String URL_ACCOUNT_ADDWXRECHARGEMONEY;
	
	@Value("${service.url_account_set_paypswd}")
    private String URL_ACCOUNT_SET_PAYPSWD;
	
	@Value("${service.url_account_change_paypswd}")
    private String URL_ACCOUNT_CHANGE_PAYPSWD;
	
	@Value("${service.url_account_reset_paypswd}")
    private String URL_ACCOUNT_RESET_PAYPSWD;

	@Value("${service.url_balance_checkpswd}")
    private String URL_BALANCE_CHECKPSWD;
	
	@Value("${service.url_balance_lock}")
    private String URL_BALANCE_LOCK;
	
	@Value("${service.url_balance_unlock}")
    private String URL_BALANCE_UNLOCK;
	
	@Value("${service.url_balance_reduce}")
    private String URL_BALANCE_REDUCE;

    @Value("${service.url_recharge_list}")
    private String URL_RECHARGE_LIST;

    @Value("${card.paymen.level_vpass_price}")
    private String LEVEL_VPASS_PRICE;

	@Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RechargePayRecordService rechargePayRecordService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private GiftCategoryService giftCategoryService;
    @Autowired
    private GiftMemberCardService giftMemberCardService;

    @Override
    public String rechargeRequest(String tid, String userId, String phone, double totalAmount, int requestType) {

        String proid = IdWorkerUtil.generateStringId();
        int type = 4, entry = 3;
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("t_id", tid);
        requestMap.add("userid", userId);
        requestMap.add("phone", phone);
        requestMap.add("proid", proid);
        requestMap.add("prodName", (totalAmount + "元"));
        requestMap.add("mobile", phone);
        requestMap.add("totalamount", totalAmount);
        requestMap.add("type", type); //游物欧品充值
        requestMap.add("entry", entry); //微信卡包
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestMap, null);
        RechargeReqResult result = restTemplate.postForObject(URL_RECHARGE_REQUEST, request, RechargeReqResult.class);

        RechargePayRecord rechargePayRecord = ContextManager.getValue(RechargePayRecord.CONTEXT_KEY);
        CardPayRecord cardPayRecord = ContextManager.getValue(CardPayRecord.CONTEXT_KEY);
        if (result.isSuccess()) {
            // 记录数据
            if (rechargePayRecord != null) {
                rechargePayRecord.setUserId(userId);
                rechargePayRecord.setPhone(phone);
                rechargePayRecord.setProId(proid);
                rechargePayRecord.setTotalAmount(totalAmount);
                rechargePayRecord.setType(type);
                rechargePayRecord.setEntry(entry);
            }

            if (cardPayRecord != null) {
                cardPayRecord.setUserId(userId);
                cardPayRecord.setPhone(phone);
                cardPayRecord.setProId(proid);
                cardPayRecord.setTotalAmount(totalAmount);
                cardPayRecord.setType(type);
                cardPayRecord.setEntry(entry);
            }

            return result.getData().getOrderNo();
        } else {
            if (rechargePayRecord != null) {
                rechargePayRecord.setTid(tid);
                rechargePayRecord.setProId(proid);
            }

            if (cardPayRecord != null) {
                cardPayRecord.setTid(tid);
                cardPayRecord.setProId(proid);
            }
            throw new RechargeRequestException(result.getMsg(), result.getState());
        }
    }

    @Override
    public RechargePayResult rechargePayment(String tid, String userId, String phone, String orderNo, String openId) {
        RechargePayRecord rechargePayRecord = ContextManager.getValue(RechargePayRecord.CONTEXT_KEY);
        CardPayRecord cardPayRecord = ContextManager.getValue(CardPayRecord.CONTEXT_KEY);

        int payChannel = 3, type = 5;
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("t_id", tid);
        requestMap.add("userid", userId);
        requestMap.add("phone", phone);
        requestMap.add("paychannel", payChannel);    //微信
        requestMap.add("type", type);                //游物欧品卡包充值
        requestMap.add("openid", openId);
        requestMap.add("orderno", orderNo);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestMap, null);
        String url;
        if (WxUtil.isAppletRequest()) {
            url = URL_RECHARGE_APPLET_PAYMENT;
        } else {
            url = URL_RECHARGE_PAYMENT;
        }
        String result = restTemplate.postForObject(url, request, String.class);

        // 记录数据
        RechargePayResult stateResult = (RechargePayResult) JsonUtil.fromJson(result, RechargePayResult.class);
        if (rechargePayRecord != null) {
            rechargePayRecord.setPaychannel(payChannel);
            rechargePayRecord.setOrderNo(orderNo);
            if (stateResult.isSuccess()) {
                rechargePayRecord.setStatus(RechargePayRecord.STATUS_WAIT_PAY);
            } else {
                rechargePayRecord.setStatus(RechargePayRecord.STATUS_ERROR);
                rechargePayRecord.setErrMsg(stateResult.getMsg());
            }
        }
        if (cardPayRecord != null) {
            cardPayRecord.setPaychannel(payChannel);
            cardPayRecord.setOrderNo(orderNo);
            if (stateResult.isSuccess()) {
                cardPayRecord.setStatus(CardPayRecord.STATUS_WAIT_PAY);
            } else {
                cardPayRecord.setStatus(CardPayRecord.STATUS_ERROR);
                cardPayRecord.setErrMsg(stateResult.getMsg());
            }
        }

        return stateResult;
    }

    @Override
    public BalanceLockResult balanceLock(String tid, String userId, String phone, double money, String orderNo) {
        MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<>();
        balanceMap.add("tid", tid);
        balanceMap.add("buyerCellphone", phone);
        balanceMap.add("userid", userId);
        balanceMap.add("money", money);
        balanceMap.add("serversn", orderNo);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(balanceMap, null);
        BalanceLockResult result = restTemplate.postForObject(URL_BALANCE_LOCK, request, BalanceLockResult.class);

        //TODO 金额不足，锁定失败
        return result;
    }

    @Override
    public StateResult balanceUnLock(String tid, String userId, String phone, double money, String orderNo) {
        MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<>();
        balanceMap.add("tid", tid);
        balanceMap.add("buyerCellphone", phone);
        balanceMap.add("userid", userId);
        balanceMap.add("money", money);
        balanceMap.add("serversn", orderNo);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(balanceMap, null);
        StateResult result = restTemplate.postForObject(URL_BALANCE_UNLOCK, request, StateResult.class);

        return result;
    }

    @Override
    public StateResult balanceReduce(String tid, String userId, String phone, double money, String orderNo) {
        MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<>();
        balanceMap.add("tid", tid);
        balanceMap.add("buyerCellphone", phone);
        balanceMap.add("userid", userId);
        balanceMap.add("haslocked", "1");
        balanceMap.add("serversn", orderNo);
        balanceMap.add("info", String.format("%1$s,%2$f,%3$s", orderNo, money, "1"));
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(balanceMap, null);
        StateResult result = restTemplate.postForObject(URL_BALANCE_REDUCE, request, StateResult.class);

        return result;
    }

    @Override
    public StateResult balanceCheckPswd(String tid, String userId, String payPswd) {
        MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<>();
        balanceMap.add("tid", tid);
        balanceMap.add("userid", userId);
        balanceMap.add("paypwd", payPswd);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(balanceMap, null);
        StateResult result = restTemplate.postForObject(URL_BALANCE_CHECKPSWD, request, StateResult.class);

        return result;
    }

    @Override
    public String getBalance(String tid, String userId, String phone) {
        MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<String, Object>();
        balanceMap.add("tid", tid);
        balanceMap.add("phone", phone);
        balanceMap.add("userid", userId);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(balanceMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_BALANCE, request, String.class);
    }

    @Override
    @Transactional
    public StateResult rechargeSuccess(String tid, String userId, String phone, String orderNo) {
        RechargePayRecord record = rechargePayRecordService.getByOrderNo(orderNo);
        if (record != null) {
            MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<String, Object>();
            balanceMap.add("tid", tid);
            balanceMap.add("phone", phone);
            balanceMap.add("userid", userId);
            balanceMap.add("money", record.getTotalAmount());
            String md5Str = MD5Util.getMD5String("wallet" + tid + userId + phone + record.getTotalAmount());
            balanceMap.add("md5str", md5Str);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(balanceMap, null);
            LoggerManager.debug("url:" + URL_ACCOUNT_ADDWXRECHARGEMONEY + ",request:" + JsonUtil.toJson(request));
            StateResult result = restTemplate.postForObject(URL_ACCOUNT_ADDWXRECHARGEMONEY, request, StateResult.class);

            if (result.isSuccess()) {
                rechargePayRecordService.orderFinished(orderNo);
            }
            return result;
        } else {
            throw new NotExistOrderException();
        }
    }

    @Override
    public String recordList(String tid, String userId, String phone, int pageIndex, int pageSize) {
        MultiValueMap<String, Object> balanceListMap = new LinkedMultiValueMap<String, Object>();
        balanceListMap.add("tid", tid);
        balanceListMap.add("phone", phone);
        balanceListMap.add("userid", userId);
        balanceListMap.add("pagenum", pageIndex);
        balanceListMap.add("pagesize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(balanceListMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_BALANCE_LIST, request, String.class);
    }

    @Override
    public String recordDetail(String tid, String recordId, String userId, String phone) {
        MultiValueMap<String, Object> recordDetailMap = new LinkedMultiValueMap<String, Object>();
        recordDetailMap.add("tid", tid);
        recordDetailMap.add("phone", phone);
        recordDetailMap.add("userid", userId);
        recordDetailMap.add("recordid", recordId);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(recordDetailMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_RECORDDETAIL, request, String.class);
    }

    @Override
    public String setPayPassword(String tid, String userId, String payPswd, String phone) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
        requestMap.add("tid", tid);
        requestMap.add("buyerCellphone", phone);
        requestMap.add("userid", userId);
        requestMap.add("paypwd", MD5Util.getMD5String(payPswd).toUpperCase());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(requestMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_SET_PAYPSWD, request, String.class);
    }

    @Override
    public String changePayPassword(String tid, String userId, String oldPayPawd, String newPayPswd) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
        requestMap.add("tid", tid);
        requestMap.add("userid", userId);
        requestMap.add("oldpaypwd", MD5Util.getMD5String(oldPayPawd).toUpperCase());
        requestMap.add("newpaypwd", MD5Util.getMD5String(newPayPswd).toUpperCase());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(requestMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_CHANGE_PAYPSWD, request, String.class);
    }

    @Override
    public String resetPayPassword(String tid, String userId, String payPswd) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
        requestMap.add("tid", tid);
        requestMap.add("userid", userId);
        requestMap.add("paypwd", MD5Util.getMD5String(payPswd).toUpperCase());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(requestMap, null);
        return restTemplate.postForObject(URL_ACCOUNT_RESET_PAYPSWD, request, String.class);
    }


    /**
     * 订单校验
     * @param orderNo
     * @return
     */
    @Override
    public void checkOrderPaySuccess(String orderNo) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
        requestMap.add("ordersn", orderNo);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(requestMap, null);
        StateResult result =  restTemplate.postForObject(URL_RECHARGE_LIST, request, StateResult.class);

        String payStatus = "";
        String payStatusName = "";

        if (result.isSuccess()) {
            try {
                payStatus = (String)((LinkedHashMap)((ArrayList)((LinkedHashMap)result.getData()).get("orderList")).get(0)).get("payStatus");
                payStatusName = (String)((LinkedHashMap)((ArrayList)((LinkedHashMap)result.getData()).get("orderList")).get(0)).get("payStatusName");
            } catch (Exception e) {
                throw new RemoteDateErrorException();
            }

            if (payStatus.endsWith("40")) {
                return;
            } else {
                LoggerManager.error(ErrorConstants.ERR_CRM_ORDER_PAY_STATUS + ":" + ErrorConstants.ERR_CRM_ORDER_PAY_STATUS_MSG+", 当前状态:" + payStatus);
                throw new RechargePayException(ErrorConstants.ERR_CRM_ORDER_PAY_STATUS_MSG, ErrorConstants.ERR_CRM_ORDER_PAY_STATUS);
            }
        } else {
            throw new RechargeRequestException(result.getMsg(), result.getState());
        }
    }

    @Override
    public Boolean payParamCheck(GiftCardPayRequest request, String userId) {

        HashSet set = new HashSet();
        int totalNumber = 0;
        String totalAmount = "0";

        List<GiftCategory> cardCategoryList = giftCategoryService.getCategoryList();
        Map<String, Double> map = new HashMap();
        for (GiftCategory giftCategory : cardCategoryList) {
            map.put(giftCategory.getId(), giftCategory.getPrice());
        }

        //循环计算订单总数量，卡面总金额
        for (GiftCardPayDetail giftCardPayDetail : request.getGiftCardPayDetails()) {

            //订单参数异常，订单giftCategoryId不存在，订单giftCategoryId对应的金额错误
            if (map.get(giftCardPayDetail.getGiftCategoryId()) == null || map.get(giftCardPayDetail.getGiftCategoryId()) != giftCardPayDetail.getPrice()) {
                throw new RechargePayException(ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER_MSG, ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER);
            }
            set.add(giftCardPayDetail.getGiftCategoryId());
            totalNumber = totalNumber + giftCardPayDetail.getNumber();
            totalAmount = MoneyUtil.moneyAdd(totalAmount, Double.toString(giftCardPayDetail.getPrice()));
        }

        //总金额 = 卡面总金额 + vpass卡金额
        if (request.getCardLevel() == MemberRegister.MLEVEL_VPASS) {
            List<GiftMemberCard> giftMemberCardList = giftMemberCardService.getInnerCard(userId);
            if (giftMemberCardList != null) {
                //购买vpass卡数小于等于礼包中的vpass卡数
                if (request.getTotalNumber() > giftMemberCardList.size() ) {
                    totalAmount = MoneyUtil.moneyAdd(totalAmount,MoneyUtil.moneyMul(LEVEL_VPASS_PRICE, Integer.toString(totalNumber-giftMemberCardList.size())));
                }
            } else {
                totalAmount = MoneyUtil.moneyAdd(totalAmount,MoneyUtil.moneyMul(LEVEL_VPASS_PRICE, Integer.toString(totalNumber)));
            }
        } else if (request.getCardLevel() == MemberRegister.MLEVEL_NORMAL) {
        } else {
            throw new RechargePayException(ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER_MSG, ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER);
        }

        //计算总金额与参数总金额比较，0为相等
        if (MoneyUtil.moneyComp(Double.toString(request.getTotalAmount()), totalAmount) == 0) {
            return true;
        } else {
            throw new RechargePayException(ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER_MSG, ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER);
        }
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
}
