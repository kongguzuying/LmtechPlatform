package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class OrderStatusException extends ErrorCodeException {

    public OrderStatusException() {
        super(ErrorConstants.ERR_CRM_ORDER_AMOUNT_CHECK_MSG, ErrorConstants.ERR_CRM_ORDER_AMOUNT_CHECK);
    }

    public OrderStatusException(String message) {
        super(message, -1);
    }

    public OrderStatusException(String message, long errorCode) {
        super(message, errorCode);
    }

    public OrderStatusException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public OrderStatusException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public OrderStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
