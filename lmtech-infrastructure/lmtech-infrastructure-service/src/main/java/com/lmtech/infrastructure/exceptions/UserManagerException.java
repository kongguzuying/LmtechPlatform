package com.lmtech.infrastructure.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

public class UserManagerException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public UserManagerException() {
		super();
	}

	public UserManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserManagerException(String message) {
		super(message);
	}

	public UserManagerException(Throwable cause) {
		super(cause);
	}

}
