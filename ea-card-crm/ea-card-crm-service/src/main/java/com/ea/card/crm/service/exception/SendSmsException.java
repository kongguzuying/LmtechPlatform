package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

/**
 * 发送短信异常
 * @author huang.jb
 */
public class SendSmsException extends ErrorCodeException {
    public SendSmsException() {
        super(ErrorConstants.ERR_SEND_SMS_ERROR_MSG, ErrorConstants.ERR_SEND_SMS_ERROR);
    }

    public SendSmsException(String message, long errorCode) {
        super(message, errorCode);
    }

    public SendSmsException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public SendSmsException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public SendSmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
