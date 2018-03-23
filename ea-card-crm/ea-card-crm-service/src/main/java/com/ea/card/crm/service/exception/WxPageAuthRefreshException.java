package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class WxPageAuthRefreshException extends ErrorCodeException {

    public WxPageAuthRefreshException(){
        super(ErrorConstants.ERR_WX_PAGE_AUTH_REFRESH_ERROR_MSG, ErrorConstants.ERR_WX_PAGE_AUTH_REFRESH_ERROR);
    }

    public WxPageAuthRefreshException(String message, long errorCode) {
        super(message, errorCode);
    }

    public WxPageAuthRefreshException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public WxPageAuthRefreshException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public WxPageAuthRefreshException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
