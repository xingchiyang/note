package com.xc.filter;

import com.xc.cache.LoginCache;
import com.xc.constant.Constant;
import com.xc.exception.NoteException;
import com.xc.exception.NoteExpCode;
import com.xc.logic.UserLogic;
import com.xc.util.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by yb on 2017/4/9 0009.
 */
@Component
public class SecurityControlFilter implements Filter {
	private String[] URL = new String[] { "/note/api/v1/system/login", "/note/api/v1/user/create" };

	@Autowired
	private LoginCache loginCache;
	@Autowired
	private UserLogic userLogic;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String url = request.getRequestURI();
		for (String _url : URL) {
			if (url.endsWith(_url)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String token = null;
		String userId = null;
		Cookie[] cookies = request.getCookies();

		userId = getFromCookie(Constant.USERID, cookies);
		SecurityContextHolder.add(userId);
		if (userId == null || userLogic.getUserById(userId) == null) {
			// 用户不存在
			throw401Exception();
		}

		token = getFromCookie(Constant.TOKEN, cookies);
		Long tokenTime = loginCache.getToken(token);
		if (tokenTime == null) {
			throw401Exception();
		} else {
			// 通过，更新token。
			Date currentTime = new Date();
			loginCache.setToken(token, currentTime.getTime());
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	private void throw401Exception() {
		throw new NoteException(NoteExpCode.EXP_CODE_NOT_AUTH, "请先登录");
	}

	private String getFromCookie(String key, Cookie[] cookies) {
		String value = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(key)) {
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}

	@Override
	public void destroy() {

	}
}
