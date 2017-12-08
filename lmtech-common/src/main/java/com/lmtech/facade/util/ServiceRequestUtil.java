package com.lmtech.facade.util;

import com.lmtech.facade.request.RequestInfo;
import com.lmtech.facade.request.StringRequest;

/**
 * 请求工具类
 * @author huang.jb
 */
public class ServiceRequestUtil {
    public static StringRequest buildStringRequest(String value) {
        StringRequest request = new StringRequest();
        request.setReqData(value);
        request.setReqInfo(buildServiceRequestInfo());
        return request;
    }

    private static RequestInfo buildServiceRequestInfo() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setComment("服务内部调用");
        return requestInfo;
    }
}
