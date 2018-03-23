package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

public class NoPermissionViewException extends ErrorCodeException {
   public NoPermissionViewException() {
        super(ErrorConstants.ERR_NO_PERMISSION_VIEW_MSG, ErrorConstants.ERR_NO_PERMISSION_VIEW);
    }

    public NoPermissionViewException(String message, long errorCode) {
        super(message, errorCode);
    }

    public NoPermissionViewException(String message, Throwable cause, long errorCode) {
        super(message, cause, errorCode);
    }

    public NoPermissionViewException(Throwable cause, long errorCode) {
        super(cause, errorCode);
    }

    public NoPermissionViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
