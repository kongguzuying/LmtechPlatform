package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class UcIntfInvokeException extends ErrorCodeException {

    public UcIntfInvokeException() {
        super(ErrorConstants.ERR_UC_INVOKEAPI_ERROR_MSG, ErrorConstants.ERR_UC_INVOKEAPI_ERROR);
    }

    public UcIntfInvokeException(String message, long errorCode) {
        super(message, errorCode);
    }

    public UcIntfInvokeException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public UcIntfInvokeException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public UcIntfInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
