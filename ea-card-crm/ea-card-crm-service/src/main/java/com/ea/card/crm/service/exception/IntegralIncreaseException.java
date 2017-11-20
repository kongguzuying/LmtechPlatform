package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class IntegralIncreaseException extends ErrorCodeException {
    public IntegralIncreaseException(String message, long errorCode) {
        super(message, errorCode);
    }

    public IntegralIncreaseException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public IntegralIncreaseException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public IntegralIncreaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
