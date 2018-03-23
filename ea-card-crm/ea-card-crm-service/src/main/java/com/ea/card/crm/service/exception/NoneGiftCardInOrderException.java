package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

/**
 * 订单中没有礼品卡异常
 * @author
 */
public class NoneGiftCardInOrderException extends ErrorCodeException {
    public NoneGiftCardInOrderException() {
        super(ErrorConstants.ERR_CRM_GIFTCARD_NOTEXIST_INORDER_MSG, ErrorConstants.ERR_CRM_GIFTCARD_NOTEXIST_INORDER);
    }

    public NoneGiftCardInOrderException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoneGiftCardInOrderException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoneGiftCardInOrderException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoneGiftCardInOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
