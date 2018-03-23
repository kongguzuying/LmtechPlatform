package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.ApkPackage;

/**
 * 移动端安装包数据请求
 * Created by huang.jb on 2017-3-27.
 */
public class ApkPackageRequest extends CommonRequest<ApkPackage> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("移动安装包数据不允许为空");
        }
    }
}
