package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoExistNoprizeProductException extends ErrorCodeException {

    public NoExistNoprizeProductException() {
        super(ErrorConstants.ERR_CRM_NOPRIZE_PRODUCT_NOTEXIST_MSG, ErrorConstants.ERR_CRM_NOPRIZE_PRODUCT_NOTEXIST);
    }

    public NoExistNoprizeProductException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoExistNoprizeProductException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoExistNoprizeProductException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoExistNoprizeProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
