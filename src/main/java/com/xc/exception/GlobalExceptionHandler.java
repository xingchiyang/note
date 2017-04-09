package com.xc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yb on 2017/4/9 0009.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = NoteException.class)
	@ResponseBody
	public RespContent exceptionHandler(HttpServletRequest req, NoteException e) {
		RespContent rep = new RespContent();
//		rep.setStatus(response.getStatus());
		if (e instanceof NoteException) {
			rep.setErrCode(((NoteException) e).getErrCode());
		}
		rep.setMessage(e.getMessage());
		return rep;
	}
}
