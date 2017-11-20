package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NotExistOrderException extends ErrorCodeException {

    public NotExistOrderException() {
        super(ErrorConstants.ERR_CRM_ORDER_NOTEXIST_MSG, ErrorConstants.ERR_CRM_ORDER_NOTEXIST);
    }

    public NotExistOrderException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NotExistOrderException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NotExistOrderException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NotExistOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
