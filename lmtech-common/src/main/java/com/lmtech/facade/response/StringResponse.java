package com.lmtech.facade.response;

/**
 * 服务字符串响应
 * Created by huang.jb on 2017-3-27.
 */
public class StringResponse extends CommonResponse<String> {
    public StringResponse() {

    }

    public StringResponse(String value) {
        super.setData(value);
    }
}
