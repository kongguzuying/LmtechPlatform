package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.MemberRegister;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class MemberRegisterRequest extends CommonRequest<MemberRegister> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入会员注册数据不允许为空");
        }
    }
}
