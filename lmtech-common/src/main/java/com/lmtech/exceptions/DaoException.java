package com.lmtech.exceptions;

/**
 * Dao层抛出的异常
 * @author huang.jb
 *
 */
public class DaoException extends LmExceptionBase {
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super("数据请求过程中出现问题，请联系管理员。");
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
