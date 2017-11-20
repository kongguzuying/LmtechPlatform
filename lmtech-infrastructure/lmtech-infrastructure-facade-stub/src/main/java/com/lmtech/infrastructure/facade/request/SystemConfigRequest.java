package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.SystemConfig;

/**
 * 系统参数数据请求
 * Created by huang.jb on 2017-3-17.
 */
public class SystemConfigRequest extends CommonRequest<SystemConfig> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入系统参数数据不允许为空");
        }
    }
}
