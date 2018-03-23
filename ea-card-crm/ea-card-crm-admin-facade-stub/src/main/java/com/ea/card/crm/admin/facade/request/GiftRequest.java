package com.ea.card.crm.admin.facade.request;

import com.ea.card.crm.model.GiftCategory;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;

/**
 * 角色数据请求
 * Created by huang.jb on 2017-3-29.
 */
public class GiftRequest extends CommonRequest<GiftCategory> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入礼品分类数据不允许为空");
        }
    }
}
