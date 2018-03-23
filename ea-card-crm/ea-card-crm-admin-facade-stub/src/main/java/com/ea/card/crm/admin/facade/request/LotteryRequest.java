package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

public class LotteryRequest extends CommonRequest<LotteryProduct> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入积分抽奖产品数据不允许为空");
        }
    }
}
