package com.ea.card.crm.service.exception;

import com.lmtech.exceptions.ErrorCodeException;

public class ActiveMemberCardException extends ErrorCodeException {
    public ActiveMemberCardException(String message) {
        super(message, -1);
    }

    public ActiveMemberCardException(String message, long errorCode) {
        super(message, errorCode);
    }

    public ActiveMemberCardException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public ActiveMemberCardException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public ActiveMemberCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
