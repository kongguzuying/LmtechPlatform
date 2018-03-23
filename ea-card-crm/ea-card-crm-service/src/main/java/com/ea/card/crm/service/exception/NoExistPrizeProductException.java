package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoExistPrizeProductException extends ErrorCodeException {
    public NoExistPrizeProductException() {
        super(ErrorConstants.ERR_CRM_PRIZE_PRODUCT_NOTEXIST_MSG, ErrorConstants.ERR_CRM_PRIZE_PRODUCT_NOTEXIST);
    }

    public NoExistPrizeProductException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoExistPrizeProductException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoExistPrizeProductException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoExistPrizeProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
