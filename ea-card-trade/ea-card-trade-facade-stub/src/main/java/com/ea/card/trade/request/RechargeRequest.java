package com.ea.card.trade.request;

import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.StringUtil;

/**
 * 充值金额请求
 * @author huang.jb
 */
public class RechargeRequest extends CommonRequest<RechargeParam> {
    @Override
    public void validate() {
        RechargeParam param = getReqData();
        if (param == null) {
            throw new IllegalArgumentException("传入参数不允许为空");
        }
        if (StringUtil.isNullOrEmpty(param.getOpenId())) {
            throw new IllegalArgumentException("传入参数openId不允许为空");
        }
        if (StringUtil.isNullOrEmpty(param.getUserId())) {
            throw new IllegalArgumentException("传入参数userId不允许为空");
        }
        if (param.getTotalAmount() < 0) {
            throw new IllegalArgumentException("传入参数totalAmount不允许小于0");
        }
    }
}
