package com.lmtech.auth.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.StringUtil;

/**
 * Token校验请求
 * Created by huang.jb on 2017-4-11.
 */
public class TokenValidateRequest extends CommonRequest<Object> {

    private boolean returnAccountInfo;

    public boolean isReturnAccountInfo() {
        return returnAccountInfo;
    }

    public void setReturnAccountInfo(boolean returnAccountInfo) {
        this.returnAccountInfo = returnAccountInfo;
    }

    @Override
    public void validate() {
        if (StringUtil.isNullOrEmpty(super.getToken())) {
            throw new RequestValidateException("传入校验Token值不允许为空");
        }
    }
}
