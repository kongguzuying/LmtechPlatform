package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class RechargePayException extends ErrorCodeException {
    public RechargePayException(String message, long errorCode) {
        super(message, errorCode);
    }

    public RechargePayException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public RechargePayException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public RechargePayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
