package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.Menu;

/**
 * 菜单数据请求
 * Created by huang.jb on 2017-3-17.
 */
public class MenuRequest extends CommonRequest<Menu> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入菜单数据不允许为空");
        }
    }
}
