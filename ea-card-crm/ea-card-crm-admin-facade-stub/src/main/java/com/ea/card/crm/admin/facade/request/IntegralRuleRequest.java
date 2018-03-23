package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.IntegralRule;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class IntegralRuleRequest extends CommonRequest<IntegralRule> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入积分规则数据不允许为空");
        }
    }
}
