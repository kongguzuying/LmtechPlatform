package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class WxPageAuthException extends ErrorCodeException{
    public WxPageAuthException() {
        super(ErrorConstants.ERR_WX_PAGE_AUTH_ERROR_MSG, ErrorConstants.ERR_WX_PAGE_AUTH_ERROR);
    }

    public WxPageAuthException(String message, long errorCode) {
        super(message, errorCode);
    }

    public WxPageAuthException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public WxPageAuthException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public WxPageAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
