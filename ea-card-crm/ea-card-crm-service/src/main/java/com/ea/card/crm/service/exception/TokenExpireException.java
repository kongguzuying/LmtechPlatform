package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

/**
 * Token过期异常
 */
public class TokenExpireException extends ErrorCodeException {
    public TokenExpireException() {
        super(ErrorConstants.ERR_CRM_TOKEN_EXPIRE_ERROR_MSG, ErrorConstants.ERR_CRM_TOKEN_EXPIRE_ERROR);
    }

    public TokenExpireException(String message, long errorCode) {
        super(message, errorCode);
    }

    public TokenExpireException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public TokenExpireException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public TokenExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
