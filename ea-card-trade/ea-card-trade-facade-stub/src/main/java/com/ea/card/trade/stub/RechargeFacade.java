package com.ea.card.trade.stub;

import com.ea.card.trade.request.PaymentRequest;
import com.ea.card.trade.request.RechargeRequest;
import com.ea.card.trade.response.RechargePaymentResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 金钱充值入口
 * @author huang.jb
 */
@FeignClient(name = "ea-card-trade")
@RequestMapping(value = "/recharge")
public interface RechargeFacade {
    /**
     * 充值请求，返回预支付订单ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    StringResponse recharge(@RequestBody RechargeRequest request);

    /**
     * 充值支付，返回支付结果
     * @param request
     * @return
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    RechargePaymentResponse payment(@RequestBody PaymentRequest request);
}
