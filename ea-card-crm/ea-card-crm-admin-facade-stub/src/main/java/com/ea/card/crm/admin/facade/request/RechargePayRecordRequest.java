package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class RechargePayRecordRequest extends CommonRequest<RechargePayRecord> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入充值记录数据不允许为空");
        }
    }
}
