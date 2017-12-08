package com.lmtech.facade.request;

import com.lmtech.facade.validator.RequestValidator;

import java.io.Serializable;

/**
 * 服务公共请求
 * Created by huang.jb on 2017-1-11.
 */
public abstract class CommonRequest<T> implements Serializable, RequestValidator {
    private RequestInfo reqInfo;    //请求信息
    private T reqData;              //请求数据
    private String token;           //Token

    public RequestInfo getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(RequestInfo reqInfo) {
        this.reqInfo = reqInfo;
    }

    public T getReqData() {
        return reqData;
    }

    public void setReqData(T reqData) {
        this.reqData = reqData;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
