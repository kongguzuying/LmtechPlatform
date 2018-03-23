package com.lmtech.facade.response;

import java.io.Serializable;

/**
 * 服务公共响应
 * Created by huang.jb on 2017-1-11.
 */
public class CommonResponse<T> implements Serializable {
    private boolean success;        //是否成功
    private long code;              //响应状态码
    private T data;                 //响应数据
    private Exception error;        //错误对象
    private String message;         //错误信息
    private String tid;             //业务流水号

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
