package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.LoginFrontSerice;
import com.xc.cache.LoginCache;
import com.xc.constant.Constant;
import com.xc.entity.User;
import com.xc.logic.UserLogic;
import com.xc.util.Des;
import com.xc.util.GenerateUUID;
import com.xc.util.RestReturnUtil;
import com.xc.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by yb on 2017/4/9 0009.
 */
@RestController
@RequestMapping(value = "/api/v1/system", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class LoginFrontServiceImpl implements LoginFrontSerice {
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private LoginCache loginCache;

	@Override
	@PostMapping("/login")
	public String login(@RequestBody String jsonStr, HttpServletResponse response) {
		ValidateUtil.validateStrBlank(jsonStr, "请求参数为空");
		User user = JSON.parseObject(jsonStr, User.class);
		ValidateUtil.validateStrBlank(user.getUsername(), "用户名不能为空");
		ValidateUtil.validateStrBlank(user.getPasswd(), "密码不能为空");
		User userByUsername = userLogic.getUserByUsername(user.getUsername());
		ValidateUtil.validateTrue(userByUsername != null, "用户名不存在");
		ValidateUtil.validateTrue(Des.encryptBasedDes(user.getPasswd()).equals(userByUsername.getPasswd()), "密码错误");

		String token = GenerateUUID.getUUID32();
		loginCache.setToken(token, new Date().getTime());
		Cookie cookie = new Cookie(Constant.TOKEN, token);
		cookie.setMaxAge(3600);
		cookie.setPath("/");
		response.addCookie(cookie);
		Cookie userIdCookie = new Cookie(Constant.USERID, userByUsername.getId());
		response.addCookie(userIdCookie);
		return RestReturnUtil.toObject("status", "success");
	}

	@Override
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.TOKEN)) {
					String token = cookie.getValue();
					loginCache.removeToken(token);
				}
			}
		}
		return null;
	}
}
