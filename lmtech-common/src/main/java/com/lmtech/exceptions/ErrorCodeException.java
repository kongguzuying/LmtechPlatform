package com.lmtech.exceptions;

/**
 * 带错误码的异常
 * Created by huang.jb on 2017-3-8.
 */
public class ErrorCodeException extends LmExceptionBase {

    private long errorCode = -1;

    private ErrorCodeException(long errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, Throwable cause, long errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(Throwable cause, long errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }
}
