package com.lmtech.facade.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

/**
 * 请求校验异常
 * Created by huang.jb on 2017-3-30.
 */
public class RequestValidateException extends LmExceptionBase {
    public RequestValidateException() {
    }

    public RequestValidateException(String message) {
        super(message);
    }

    public RequestValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestValidateException(Throwable cause) {
        super(cause);
    }

    public RequestValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
