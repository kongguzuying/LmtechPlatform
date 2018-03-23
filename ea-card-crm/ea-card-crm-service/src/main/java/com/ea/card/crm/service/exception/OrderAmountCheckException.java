package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class OrderAmountCheckException extends ErrorCodeException {

    public OrderAmountCheckException() {
        super(ErrorConstants.ERR_CRM_ORDER_AMOUNT_CHECK_MSG, ErrorConstants.ERR_CRM_ORDER_AMOUNT_CHECK);
    }

    public OrderAmountCheckException(String message) {
        super(message, -1);
    }

    public OrderAmountCheckException(String message, long errorCode) {
        super(message, errorCode);
    }

    public OrderAmountCheckException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public OrderAmountCheckException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public OrderAmountCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
