package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NotExistCardActiveRecord extends ErrorCodeException {
    public NotExistCardActiveRecord() {
        super(ErrorConstants.ERR_CRM_NOT_EXIST_CARD_ACTIVE_RECORD_MSG, ErrorConstants.ERR_CRM_NOT_EXIST_CARD_ACTIVE_RECORD);
    }

    public NotExistCardActiveRecord(String message, long errorCode) {
        super(message, errorCode);
    }

    public NotExistCardActiveRecord(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NotExistCardActiveRecord(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NotExistCardActiveRecord(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
