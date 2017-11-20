package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class NoneRegisterException extends ErrorCodeException {
    public NoneRegisterException() {
        super("用户未注册", 300212002);
    }

    public NoneRegisterException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoneRegisterException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoneRegisterException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoneRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
