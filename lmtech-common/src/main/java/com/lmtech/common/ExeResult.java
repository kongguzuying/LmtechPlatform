package com.lmtech.common;

import java.io.Serializable;

/**
 * 执行结果
 * @author huang.jb
 *
 */
public class ExeResult implements Serializable {
	private static final long serialVersionUID = 1L;

	public ExeResult() {
		ExeResult result = new ExeResult(true, data);

	}
	public ExeResult(boolean success) {
		super();
		this.success = success;
	}
	public ExeResult(boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}
	public ExeResult(boolean success, Object data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}
	public ExeResult(boolean success, Object data, String message, long errCode) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
		this.errCode = errCode;
	}
	private String tId;;	//流水号
	private boolean success;	//是否成功
	private Object data;		//数据
	private long errCode = -1;		//错误代码
	private String message;		//信息

	/**
	 * 获取结果集
	 * @return
	 */
	public StateResult getResult() {
		StateResult result = new StateResult();
		if (success) {
			result.setState(0);
			result.setData(data);
		} else {
			result.setState(errCode);
		}
		result.setMsg(message);
		result.settId(tId);
		return result;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getErrCode() {
		return errCode;
	}
	public void setErrCode(long errCode) {
		this.errCode = errCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}
}
