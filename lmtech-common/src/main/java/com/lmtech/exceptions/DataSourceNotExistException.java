package com.lmtech.exceptions;

public class DataSourceNotExistException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public DataSourceNotExistException() {
		super();
	}

	public DataSourceNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataSourceNotExistException(String message) {
		super(message);
	}

	public DataSourceNotExistException(Throwable cause) {
		super(cause);
	}
}
