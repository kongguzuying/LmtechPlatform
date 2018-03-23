package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.model.RechargePayRecord;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class CardActiveRecordRequest extends CommonRequest<CardActiveRecord> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入会员卡激活数据不允许为空");
        }
    }
}
