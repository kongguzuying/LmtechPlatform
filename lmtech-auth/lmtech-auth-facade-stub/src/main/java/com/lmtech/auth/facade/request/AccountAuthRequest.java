package com.lmtech.auth.facade.request;

import com.lmtech.auth.facade.dto.AccountAuthData;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.StringUtil;

/**
 * 帐户请求
 * Created by huang.jb on 2017-1-12.
 */
public class AccountAuthRequest extends CommonRequest<AccountAuthData> {
    @Override
    public void validate() {
        if (getReqData() == null) {
            throw new IllegalArgumentException("请求参数不允许为空");
        }
        if (StringUtil.isNullOrEmpty(getReqData().getLoginName())) {
            throw new IllegalArgumentException("用户名不允许为空");
        }
        if (StringUtil.isNullOrEmpty(getReqData().getPassword())) {
            throw new IllegalArgumentException("密码不允许为空");
        }
    }
}
