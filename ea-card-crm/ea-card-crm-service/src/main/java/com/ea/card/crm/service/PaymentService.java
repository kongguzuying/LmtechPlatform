package com.ea.card.crm.service;

import com.ea.card.crm.facade.request.GiftCardPayRequest;
import com.ea.card.crm.service.vo.BalanceLockResult;
import com.ea.card.crm.service.vo.RechargePayResult;
import com.lmtech.common.StateResult;

/**
 * 支付相关服务
 * @author
 */
public interface PaymentService {
    public static final int ORDER_REQUEST_TYPE_RECHARGE = 1;
    public static final int ORDER_REQUEST_TYPE_CARDPAY = 2;

    /**
     * 充值请求
     * @param tid
     * @param userId
     * @param phone
     * @param totalAmount
     * @return
     */
    String rechargeRequest(String tid, String userId, String phone, double totalAmount, int requestType);

    /**
     * 充值支付
     * @param tid
     * @param userId
     * @param phone
     * @param orderNo
     * @param openId
     * @return
     */
    RechargePayResult rechargePayment(String tid, String userId, String phone, String orderNo, String openId);

    /**
     * 余额锁定
     * @param tid
     * @param userId
     * @param phone
     * @param money
     * @param orderNo
     * @return
     */
    BalanceLockResult balanceLock(String tid, String userId, String phone, double money, String orderNo);

    /**
     * 余额解除锁定
     * @param tid
     * @param userId
     * @param phone
     * @param money
     * @param orderNo
     * @return
     */
    StateResult balanceUnLock(String tid, String userId, String phone, double money, String orderNo);

    /**
     * 余额消费
     * @param tid
     * @param userId
     * @param phone
     * @param money
     * @param orderNo
     * @return
     */
    StateResult balanceReduce(String tid, String userId, String phone, double money, String orderNo);

    /**
     * 余额支付密码校验
     * @param tid
     * @param userId
     * @param payPswd
     * @return
     */
    StateResult balanceCheckPswd(String tid, String userId, String payPswd);

    /**
     * 获取余额
     * @param tid
     * @param userId
     * @param phone
     * @return
     */
    String getBalance(String tid, String userId, String phone);

    /**
     * 充值成功
     * @param tid
     * @param userId
     * @param phone
     * @param orderNo
     * @return
     */
    StateResult rechargeSuccess(String tid, String userId, String phone, String orderNo);

    /**
     * 获取余额明细
     * @param tid
     * @param userId
     * @param phone
     * @param pageIndex
     * @param pageSize
     * @return
     */
    String recordList(String tid, String userId, String phone, int pageIndex, int pageSize);

    /**
     * 交易记录详情
     * @param tid
     * @param recordId
     * @param userId
     * @param phone
     * @return
     */
    String recordDetail(String tid, String recordId, String userId, String phone);

    /**
     * 设置支付密码
     * @param tid
     * @param userId
     * @param payPswd
     * @param phone
     * @return
     */
    String setPayPassword(String tid, String userId, String payPswd, String phone);

    /**
     * 修改支付密码
     * @param tid
     * @param userId
     * @param oldPayPawd
     * @param newPayPswd
     * @return
     */
    String changePayPassword(String tid, String userId, String oldPayPawd, String newPayPswd);

    /**
     * 重置支付密码
     * @param tid
     * @param userId
     * @param payPswd
     * @return
     */
    String resetPayPassword(String tid, String userId, String payPswd);

    /**
     * 订单校验
     * @param orderNo
     * @return
     */
    void checkOrderPaySuccess(String orderNo);

    /**
     * 支付参数校验
     * @param request
     * @param userId
     * @return
     */
    Boolean payParamCheck(GiftCardPayRequest request, String userId);
}
