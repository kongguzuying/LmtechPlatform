package com.lmtech.exceptions;

/**
 * 数据版本匹配异常
 * Created by huang.jb on 2017-3-17.
 */
public class DataVersionException extends LmExceptionBase {
    public DataVersionException() {
        super("数据版本匹配失败，请先更新数据。");
    }

    public DataVersionException(String message) {
        super(message);
    }

    public DataVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataVersionException(Throwable cause) {
        super(cause);
    }

    public DataVersionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
