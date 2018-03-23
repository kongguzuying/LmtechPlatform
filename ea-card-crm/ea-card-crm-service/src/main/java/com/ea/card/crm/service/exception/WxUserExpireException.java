package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class WxUserExpireException extends ErrorCodeException {
    public WxUserExpireException() {
        super(ErrorConstants.ERR_WX_USER_EXPIRE_ERROR_MSG, ErrorConstants.ERR_WX_USER_EXPIRE_ERROR);
    }

    public WxUserExpireException(String message, long errorCode) {
        super(message, errorCode);
    }

    public WxUserExpireException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public WxUserExpireException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public WxUserExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
