package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class CardPresentingException extends ErrorCodeException {
    public CardPresentingException() {
        super(ErrorConstants.ERR_CRM_CARD_PRESENTING_MSG, ErrorConstants.ERR_CRM_CARD_PRESENTING);
    }

    public CardPresentingException(String message, long errorCode) {
        super(message, errorCode);
    }

    public CardPresentingException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public CardPresentingException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public CardPresentingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
