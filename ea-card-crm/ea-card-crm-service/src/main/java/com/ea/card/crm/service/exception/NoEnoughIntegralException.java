package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

/**
 * 积分不足异常
 * @author huang.jb
 */
public class NoEnoughIntegralException extends ErrorCodeException {
    public NoEnoughIntegralException() {
        super(ErrorConstants.ERR_INTEGRAL_LACK_ERROR_MSG, ErrorConstants.ERR_INTEGRAL_LACK_ERROR);
    }

    public NoEnoughIntegralException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoEnoughIntegralException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoEnoughIntegralException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoEnoughIntegralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
