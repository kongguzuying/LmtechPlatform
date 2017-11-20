package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NotExistCardPresentRecordException extends ErrorCodeException {
    public NotExistCardPresentRecordException() {
        super(ErrorConstants.ERR_CRM_NOT_EXIST_CARD_PRESENT_RECORD_MSG, ErrorConstants.ERR_CRM_NOT_EXIST_CARD_PRESENT_RECORD);
    }

    public NotExistCardPresentRecordException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NotExistCardPresentRecordException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NotExistCardPresentRecordException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NotExistCardPresentRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
