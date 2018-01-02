package com.ea.card.trade.request;

import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.StringUtil;

/**
 * 支付请求
 * @author huang.jb
 */
public class PaymentRequest extends CommonRequest<PaymentParam> {
    @Override
    public void validate() {
        PaymentParam param = getReqData();
        if (param == null) {
            throw new IllegalArgumentException("传入参数不允许为空");
        }
        if (StringUtil.isNullOrEmpty(param.getOpenId())) {
            throw new IllegalArgumentException("传入参数openId不允许为空");
        }
        if (StringUtil.isNullOrEmpty(param.getOrderId())) {
            throw new IllegalArgumentException("传入参数orderId不允许为空");
        }
    }
}
