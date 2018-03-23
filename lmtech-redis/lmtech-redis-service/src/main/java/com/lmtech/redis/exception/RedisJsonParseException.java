package com.lmtech.redis.exception;

import com.lmtech.exceptions.LmExceptionBase;

/**
 * Redis Json对象转换错误
 * Created by huang.jb on 2017-3-10.
 */
public class RedisJsonParseException extends LmExceptionBase {
    private String srcJson;
    private String className;

    public RedisJsonParseException(String srcJson, String className) {
        this.srcJson = srcJson;
        this.className = className;
    }

    public RedisJsonParseException(String message, String srcJson, String className) {
        super(message);
        this.srcJson = srcJson;
        this.className = className;
    }

    public RedisJsonParseException(String message, Throwable cause, String srcJson, String className) {
        super(message, cause);
        this.srcJson = srcJson;
        this.className = className;
    }

    public RedisJsonParseException(Throwable cause, String srcJson, String className) {
        super(cause);
        this.srcJson = srcJson;
        this.className = className;
    }

    public RedisJsonParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String srcJson, String className) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.srcJson = srcJson;
        this.className = className;
    }
}
