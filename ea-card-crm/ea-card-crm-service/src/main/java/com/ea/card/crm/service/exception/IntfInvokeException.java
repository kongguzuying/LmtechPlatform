package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class IntfInvokeException extends ErrorCodeException {
    public IntfInvokeException() {
        super("接口调用异常", 300212004);
    }

    public IntfInvokeException(String message, long errorCode) {
        super(message, errorCode);
    }

    public IntfInvokeException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public IntfInvokeException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public IntfInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
