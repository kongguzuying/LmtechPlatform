package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoneRegisterException extends ErrorCodeException {
    public NoneRegisterException() {
        super(ErrorConstants.ERR_NONE_REGISTER_MSG, ErrorConstants.ERR_NONE_REGISTER);
    }

    public NoneRegisterException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoneRegisterException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoneRegisterException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoneRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
