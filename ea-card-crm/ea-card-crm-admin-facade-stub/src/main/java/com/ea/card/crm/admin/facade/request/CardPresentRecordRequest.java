package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class CardPresentRecordRequest extends CommonRequest<CardPresentRecord> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入卡片赠送记录数据不允许为空");
        }
    }
}
