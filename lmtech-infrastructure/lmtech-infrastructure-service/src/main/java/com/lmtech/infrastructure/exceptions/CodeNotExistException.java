package com.lmtech.infrastructure.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

/**
 * 代码不存在异常
 * @author huang.jb
 *
 */
public class CodeNotExistException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public CodeNotExistException() {
		super();
	}

	public CodeNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeNotExistException(String message) {
		super(message);
	}

	public CodeNotExistException(Throwable cause) {
		super(cause);
	}
	
}
