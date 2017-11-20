package com.lmtech.admin.common.adaptor;

import com.lmtech.admin.common.exceptions.ServiceException;
import com.lmtech.common.Context;
import com.lmtech.common.ContextManager;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.facade.request.RequestInfo;
import com.lmtech.facade.response.CommonResponse;
import org.springframework.util.Assert;

/**
 * 服务适配器基类
 * Created by huang.jb on 2017-3-28.
 */
public class ServiceAdaptorBase  {

    private static final String APP_VERSION = "1.0.0";
    private static final String SYS_VERSION = "1.0.0";

    /**
     * 构建请求信息
     * @return
     */
    protected RequestInfo buildRequestInfo() {
        Context context = ContextManager.getContext();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserName(context.getUserName());
        requestInfo.setAccount(context.getLoginName());
        requestInfo.setAppVersion(APP_VERSION);
        requestInfo.setSysVersion(SYS_VERSION);
        requestInfo.setUserId(context.getUserId());

        return requestInfo;
    }

    /**
     * 校验返回结果
     * @param response
     */
    protected void validResponse(CommonResponse response) {
        if (!response.isSuccess()) {
            throw new ServiceException(response.getMessage());
        }
    }

    /**
     * 初始化请求
     * @param request
     */
    protected void initRequest(CommonRequest request) {
        RequestInfo requestInfo = buildRequestInfo();
        request.setReqInfo(requestInfo);

        //校验request
        request.validate();
    }
}
