package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.*;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付服务入口
 * @author
 */
@FeignClient("ea-card-crm")
@RequestMapping(value = "/payment")
public interface PaymentFacade {

    /**
     * 会员卡充值订单生成
     * @param request
     * @return
     */
    @RequestMapping(value = "/rechargeRequest", method = RequestMethod.POST)
    StateResult rechargeRequest(@RequestBody RechargePayRequest request);

    /**
     * 会员卡充值支付
     * @param request
     * @return
     */
    @RequestMapping(value = "/rechargePay", method = RequestMethod.POST)
    StateResult rechargePay(@RequestBody RechargePayRequest request);

    /**
     * 礼品卡购买支付
     * @param request
     * @return
     */
    @RequestMapping(value = "/giftCardPay", method = RequestMethod.POST)
    StateResult giftCardPay(HttpServletRequest httpRequest, @RequestBody GiftCardPayRequest request);

    /**
     * 礼品卡购买成功接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/giftCardPaySuccess", method = RequestMethod.POST)
    StateResult giftCardPaySuccess(@RequestBody GiftCardPaySuccessRequest request);

    /**
     * 查询余额
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    Object getBalance(@RequestParam String openId);

    /**
     * 充值成功接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/rechargeSuccess", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    StateResult rechargeSuccess(@RequestBody AddBalanceRequest request);

    /**
     * 获取交易记录列表
     * @param openId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/recordList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    Object recordList(@RequestParam String openId, int pageIndex, int pageSize);

    /**
     * 获取交易记录明细
     * @param recordId
     * @param openId
     * @return
     */
    @RequestMapping(value = "/recordDetail", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    Object recordDetail(@RequestParam String recordId, @RequestParam String openId);

    /**
     * 余额变更通知
     * @param userId
     */
    @RequestMapping(value = "/notifyBalanceUpdate", method = RequestMethod.POST)
    StateResult notifyBalanceUpdate(@RequestParam String userId, @RequestParam double balance);

    /**
     * 余额支付下单
     * @return
     */
    @RequestMapping(value = "/balanceOrder", method = RequestMethod.POST)
    StateResult balanceOrder(@RequestBody BalanceOrderRequest request);

    /**
     * 余额支付
     * @return
     */
    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    StateResult balancePay(@RequestBody BalancePayRequest request);

    /**
     * 设置支付密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/setPayPassword", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    Object setPayPassword(@RequestBody PayPswdChangedRequest request);

    /**
     * 修改支付密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/changePayPassword", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    Object changePayPassword(@RequestBody PayPswdChangedRequest request);

    /**
     * 重置支付密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPayPassword", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    Object resetPayPassword(@RequestBody PayPswdChangedRequest request);
}
