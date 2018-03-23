package com.lmtech.cloud.zuul.exception;

import com.lmtech.exceptions.LmExceptionBase;

public class NoneException extends LmExceptionBase {
    public NoneException() {
    }

    public NoneException(String message) {
        super(message);
    }

    public NoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneException(Throwable cause) {
        super(cause);
    }

    public NoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
