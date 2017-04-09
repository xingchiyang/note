package com.xc.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yb on 2017/4/9 0009.
 */
public interface LoginFrontSerice {

	public String login(String jsonStr, HttpServletResponse response);

	public String logout(HttpServletRequest request);

}
