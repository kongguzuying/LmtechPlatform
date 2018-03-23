package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class UserCheckException extends ErrorCodeException {
    public UserCheckException() {
        super(ErrorConstants.ERR_CRM_USER_CHECK_MSG, ErrorConstants.ERR_CRM_USER_CHECK);
    }

    public UserCheckException(String message, long errorCode) {
        super(message, errorCode);
    }

    public UserCheckException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public UserCheckException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public UserCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
