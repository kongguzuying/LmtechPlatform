package com.lmtech.exceptions;

/**
 * 平台基础异常类，所有自定义异常必须继承自该类
 * Created by huang.jb on 2017-3-10.
 */
public class LmExceptionBase extends RuntimeException {
    public LmExceptionBase() {
    }

    public LmExceptionBase(String message) {
        super(message);
    }

    public LmExceptionBase(String message, Throwable cause) {
        super(message, cause);
    }

    public LmExceptionBase(Throwable cause) {
        super(cause);
    }

    public LmExceptionBase(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
