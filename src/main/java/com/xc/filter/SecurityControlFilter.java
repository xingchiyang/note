package com.xc.filter;

import com.xc.cache.LoginCache;
import com.xc.constant.Constant;
import com.xc.exception.NoteException;
import com.xc.exception.NoteExpCode;
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
	private String[] URL = new String[] { "/note/api/v1/system/login" };

	@Autowired
	private LoginCache loginCache;

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
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(Constant.TOKEN)) {
					token = cookie.getValue();
					break;
				}
			}
		}
		Long tokenTime = loginCache.getToken(token);
		if (tokenTime == null) {
			throw new NoteException(NoteExpCode.EXP_CODE_NOT_AUTH, "请先登录");
		} else {
			// 通过，更新token。
			Date currentTime = new Date();
			loginCache.setToken(token, currentTime.getTime());
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}
}
