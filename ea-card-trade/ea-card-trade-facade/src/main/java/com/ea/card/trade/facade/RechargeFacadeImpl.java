package com.ea.card.trade.facade;

import com.ea.card.trade.exception.OrderNotExistException;
import com.ea.card.trade.model.Order;
import com.ea.card.trade.request.PaymentParam;
import com.ea.card.trade.request.PaymentRequest;
import com.ea.card.trade.request.RechargeParam;
import com.ea.card.trade.request.RechargeRequest;
import com.ea.card.trade.response.RechargePaymentData;
import com.ea.card.trade.response.RechargePaymentResponse;
import com.ea.card.trade.service.OrderService;
import com.ea.card.trade.service.RechargeService;
import com.ea.card.trade.stub.RechargeFacade;
import com.ea.card.trade.vo.UnifiedOrderParam;
import com.lmtech.facade.response.StringResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "充值交易接口")
@RestController
@RequestMapping(value = "/recharge")
public class RechargeFacadeImpl implements RechargeFacade {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RechargeService rechargeService;

    @ApiOperation("充值预支付请求发起")
    @Override
    public StringResponse recharge(@RequestBody RechargeRequest request) {
        RechargeParam rechargeParam = request.getReqData();

        Order order = new Order();
        order.setOpenId(rechargeParam.getOpenId());
        order.setEntry(rechargeParam.getEntry());
        order.setMobile(rechargeParam.getMobile());
        order.setPhone(rechargeParam.getPhone());
        order.setProdName(rechargeParam.getProdName());
        order.setProId(rechargeParam.getProId());
        order.setTotalAmount(rechargeParam.getTotalAmount());
        order.setType(rechargeParam.getType());
        order.setUserId(rechargeParam.getUserId());
        String orderId = orderService.add(order);

        StringResponse response = new StringResponse(orderId);
        response.setSuccess(true);
        return response;
    }

    @ApiOperation("充值订单支付")
    @Override
    public RechargePaymentResponse payment(@RequestBody PaymentRequest request) {
        PaymentParam paymentParam = request.getReqData();
        //TODO 校验参数

        Order order = orderService.get(paymentParam.getOrderId());
        if (order == null) {
            throw new OrderNotExistException();
        }
        String openId = order.getOpenId();

        //TODO 价格处理
        int totalFee = (int) (order.getTotalAmount() * 100);
        UnifiedOrderParam unifiedOrderParam = new UnifiedOrderParam();
        unifiedOrderParam.setOpenid(openId);
        unifiedOrderParam.setOut_trade_no(order.getId());
        unifiedOrderParam.setTotal_fee(totalFee);
        unifiedOrderParam.setDevice_info("10010");
        String prepayId = rechargeService.recharge(unifiedOrderParam);

        RechargePaymentData paymentData = new RechargePaymentData();
        paymentData.setPrepayId(prepayId);
        paymentData.setOrderId(order.getId());
        paymentData.setTotalAmount(order.getTotalAmount());
        paymentData.setTotalFee(totalFee);
        RechargePaymentResponse response = new RechargePaymentResponse();
        response.setSuccess(true);
        response.setData(paymentData);
        return response;
    }
}
