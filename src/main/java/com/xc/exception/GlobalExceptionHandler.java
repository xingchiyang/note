package com.xc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yb on 2017/4/9 0009.
 */
@ControllerAdvice(basePackages = "com.xc")
public class GlobalExceptionHandler {
	@ExceptionHandler
	@ResponseBody
	public RespContent exceptionHandler(HttpServletRequest req, Exception e, HttpServletResponse res) {
		RespContent rep = new RespContent();
		rep.setStatus(500);
		res.setStatus(500);
		if (e instanceof NoteException) {
			rep.setErrCode(((NoteException) e).getErrCode());
		}
		rep.setMessage(e.getMessage());
		return rep;
	}
}
