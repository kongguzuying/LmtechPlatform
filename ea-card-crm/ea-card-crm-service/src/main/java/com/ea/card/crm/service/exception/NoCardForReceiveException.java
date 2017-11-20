package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoCardForReceiveException extends ErrorCodeException {
    public NoCardForReceiveException() {
        super(ErrorConstants.ERR_CRM_NOCARDFORRECEIVE_MSG, ErrorConstants.ERR_CRM_NOCARDFORRECEIVE);
    }

    public NoCardForReceiveException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoCardForReceiveException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoCardForReceiveException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoCardForReceiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
