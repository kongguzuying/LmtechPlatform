package com.lmtech.infrastructure.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

/**
 * 系统参数不存在异常
 * @author huang.jb
 *
 */
public class SystemConfigNotExistException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public SystemConfigNotExistException() {
		super();
	}

	public SystemConfigNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemConfigNotExistException(String message) {
		super(message);
	}

	public SystemConfigNotExistException(Throwable cause) {
		super(cause);
	}
	
}
