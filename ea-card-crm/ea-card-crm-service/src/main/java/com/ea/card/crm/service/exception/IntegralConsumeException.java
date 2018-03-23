package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class IntegralConsumeException extends ErrorCodeException {
    public IntegralConsumeException(String message, long errorCode) {
        super(message, errorCode);
    }

    public IntegralConsumeException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public IntegralConsumeException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public IntegralConsumeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
