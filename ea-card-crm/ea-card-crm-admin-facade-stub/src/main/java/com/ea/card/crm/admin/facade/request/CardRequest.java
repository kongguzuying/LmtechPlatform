package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.CardCategory;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class CardRequest extends CommonRequest<CardCategory> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入礼品分类数据不允许为空");
        }
    }
}
