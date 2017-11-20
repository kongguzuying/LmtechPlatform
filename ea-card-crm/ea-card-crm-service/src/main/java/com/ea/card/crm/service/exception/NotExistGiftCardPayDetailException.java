package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NotExistGiftCardPayDetailException extends ErrorCodeException {
    public NotExistGiftCardPayDetailException() {
        super(ErrorConstants.ERR_CRM_NOT_EXIST_CARD_PAY_DETAIL_MSG, ErrorConstants.ERR_CRM_NOT_EXIST_CARD_PAY_DETAIL);
    }

    public NotExistGiftCardPayDetailException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NotExistGiftCardPayDetailException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NotExistGiftCardPayDetailException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NotExistGiftCardPayDetailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
