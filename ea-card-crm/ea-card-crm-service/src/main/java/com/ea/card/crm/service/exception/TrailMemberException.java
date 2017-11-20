package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class TrailMemberException extends ErrorCodeException {
    public TrailMemberException(String message, long errorCode) {
        super(message, errorCode);
    }

    public TrailMemberException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public TrailMemberException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public TrailMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
