package com.lmtech.facade.response;

/**
 * 服务布尔值响应
 * Created by huang.jb on 2017-3-29.
 */
public class BooleanResponse extends CommonResponse<Boolean> {
    public BooleanResponse() {

    }

    public BooleanResponse(boolean value) {
        super.setData(value);
    }
}
