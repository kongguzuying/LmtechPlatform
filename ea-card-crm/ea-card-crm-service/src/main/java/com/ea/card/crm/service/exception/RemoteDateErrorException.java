package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class RemoteDateErrorException extends ErrorCodeException {

    public RemoteDateErrorException() {
        super(ErrorConstants.ERR_CRM_REMOTE_DATE_ERROR_MSG, ErrorConstants.ERR_CRM_REMOTE_DATE_ERROR);
    }

    public RemoteDateErrorException(String message, long errorCode) {
        super(message, errorCode);
    }

    public RemoteDateErrorException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public RemoteDateErrorException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public RemoteDateErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
