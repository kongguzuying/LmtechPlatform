package com.lmtech.auth.exceptions;

import com.lmtech.exceptions.LmExceptionBase;

public class AuthenticateException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public AuthenticateException() {
		super();
	}

	public AuthenticateException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticateException(String message) {
		super(message);
	}

	public AuthenticateException(Throwable cause) {
		super(cause);
	}

}
