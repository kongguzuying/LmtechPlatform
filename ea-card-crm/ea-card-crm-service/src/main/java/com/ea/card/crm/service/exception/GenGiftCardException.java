package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class GenGiftCardException extends ErrorCodeException {
    public GenGiftCardException() {
        super(ErrorConstants.ERR_CRM_GEN_GIFT_CARD_MSG, ErrorConstants.ERR_CRM_GEN_GIFT_CARD);
    }

    public GenGiftCardException(String message, long errorCode) {
        super(message, errorCode);
    }

    public GenGiftCardException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public GenGiftCardException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public GenGiftCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
