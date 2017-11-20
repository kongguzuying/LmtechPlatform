package com.lmtech.exceptions;

/**
 * 管理器异常
 * @author huang.jb
 *
 */
public class ManagerException extends LmExceptionBase {

	private static final long serialVersionUID = 1L;

	public ManagerException() {
		super();
	}

	public ManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerException(String message) {
		super(message);
	}

	public ManagerException(Throwable cause) {
		super(cause);
	}

}
