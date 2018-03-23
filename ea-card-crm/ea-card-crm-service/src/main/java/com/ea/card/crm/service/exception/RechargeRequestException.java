package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class RechargeRequestException extends ErrorCodeException {
    public RechargeRequestException(String message, long errorCode) {
        super(message, errorCode);
    }

    public RechargeRequestException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public RechargeRequestException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public RechargeRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
