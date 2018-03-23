package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoneHistoryRegisterException extends ErrorCodeException {
    public NoneHistoryRegisterException() {
        super(ErrorConstants.ERR_HISTORY_NONE_REGISTER_MSG, ErrorConstants.ERR_HISTORY_NONE_REGISTER);
    }

    public NoneHistoryRegisterException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoneHistoryRegisterException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoneHistoryRegisterException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoneHistoryRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
