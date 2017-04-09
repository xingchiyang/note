package com.xc.exception;

public class RespContent {
	private int status;
	private String errCode;
	private String message;

	public RespContent() {
		super();
	}

	public RespContent(String errCode, String message) {
		this.errCode = errCode;
		this.message = message;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
