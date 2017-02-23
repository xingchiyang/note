package com.xc.exception;

import com.xc.entity.Note;

/**
 * 异常类
 */
public class NoteException extends RuntimeException {
	private String errCode;

	public NoteException() {
		super();
	}

	public NoteException(Throwable cause) {
		super(cause);
	}

	public NoteException(String errCode, String message) {
		super(message);
		this.errCode = errCode;
	}

	public NoteException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
