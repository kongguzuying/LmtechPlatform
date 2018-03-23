package com.lmtech.admin.common.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

/**
 * 服务异常
 * Created by huang.jb on 2017-3-28.
 */
public class ServiceException extends LmExceptionBase {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
