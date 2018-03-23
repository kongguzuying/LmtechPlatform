package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NotExistNoPresentCardException extends ErrorCodeException {
    public NotExistNoPresentCardException() {
        super(ErrorConstants.ERR_CRM_NOT_EXIST_NOPRESENT_CARD_MSG, ErrorConstants.ERR_CRM_NOT_EXIST_NOPRESENT_CARD);
    }

    public NotExistNoPresentCardException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NotExistNoPresentCardException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NotExistNoPresentCardException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NotExistNoPresentCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
