package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class CardPayRecordRequest extends CommonRequest<CardPayRecord> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入卡片购买记录数据不允许为空");
        }
    }
}
