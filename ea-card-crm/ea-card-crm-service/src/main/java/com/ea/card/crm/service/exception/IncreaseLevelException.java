package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class IncreaseLevelException extends ErrorCodeException {
    public IncreaseLevelException() {
        super(ErrorConstants.ERR_CRM_NONEED_INCREASE_LEVEL_MSG, ErrorConstants.ERR_CRM_NONEED_INCREASE_LEVEL);
    }

    public IncreaseLevelException(String message, long errorCode) {
        super(message, errorCode);
    }

    public IncreaseLevelException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public IncreaseLevelException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public IncreaseLevelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
