package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.UserFrontService;
import com.xc.constant.Constant;
import com.xc.entity.User;
import com.xc.logic.UserLogic;
import com.xc.util.RestReturnUtil;
import com.xc.util.SecurityContextHolder;
import com.xc.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/02/23 0023.
 */
@RestController
@RequestMapping(value = "/api/v1/user", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class UserFrontServiceImpl implements UserFrontService {
	@Autowired
	private UserLogic userLogic;

	@Override
	@PostMapping("/create")
	public String createUser(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		User user = JSON.parseObject(jsonString, User.class);
		ValidateUtil.validateStrBlank(user.getUsername(), "用户名不能为空");
		ValidateUtil.validateStrBlank(user.getPasswd(), "密码不能为空");
		User userByUsername = userLogic.getUserByUsername(user.getUsername());
		ValidateUtil.validateTrue(userByUsername == null, "用户名已被使用");
		return RestReturnUtil.toObject("id", userLogic.createUser(user));
	}

	@Override
	@PostMapping("/modify")
	public String modifyUser(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		User user = JSON.parseObject(jsonString, User.class);
		ValidateUtil.validateStrBlank(user.getUsername(), "用户名不能为空");
		ValidateUtil.validateStrBlank(user.getPasswd(), "密码不能为空");
		User userByUsername = userLogic.getUserByUsername(user.getUsername());
		if (userByUsername != null) {
			ValidateUtil.validateTrue(userByUsername.getId().equals(user.getId()), "用户名已被使用");
		}
		if (userLogic.modifyUser(user)) {
			return RestReturnUtil.toObject("id", user.getId());
		}
		return null;
	}

	@GetMapping("/get")
	@Override
	public String getUser() {
		User user = userLogic.getUserById(SecurityContextHolder.getUserId());
		return JSON.toJSONString(user);
	}
}
