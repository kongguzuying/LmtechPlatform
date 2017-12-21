package com.lmtech.infrastructure.exceptions;

import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.exceptions.LmExceptionBase;
import com.lmtech.infrastructure.constants.ErrorConstants;

/**
 * 租户不存在异常
 * @author huang.jb
 *
 */
public class TenancyNotExistException extends ErrorCodeException {

	public TenancyNotExistException() {
		super(ErrorConstants.ERR_TENANCY_NOT_EXIST_MSG, ErrorConstants.ERR_TENANCY_NOT_EXIST);
	}

	public TenancyNotExistException(String message, long errorCode) {
		super(message, errorCode);
	}

	public TenancyNotExistException(String message, Throwable cause, long errorCode) {
		super(message, cause, errorCode);
	}

	public TenancyNotExistException(Throwable cause, long errorCode) {
		super(cause, errorCode);
	}

	public TenancyNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
		super(message, cause, enableSuppression, writableStackTrace, errorCode);
	}
}
