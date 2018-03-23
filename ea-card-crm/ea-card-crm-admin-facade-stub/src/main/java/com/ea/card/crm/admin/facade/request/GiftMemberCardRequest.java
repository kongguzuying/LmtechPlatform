package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class GiftMemberCardRequest extends CommonRequest<GiftMemberCard> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入礼品会员卡数据不允许为空");
        }
    }
}
