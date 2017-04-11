package com.xc.logic.impl;

import com.xc.constant.UserConstant;
import com.xc.dao.UserDao;
import com.xc.entity.User;
import com.xc.logic.UserLogic;
import com.xc.util.Des;
import com.xc.util.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by yb on 2017/4/9 0009.
 */
@Service
public class UserLogicImpl implements UserLogic {
	@Autowired
	private UserDao userDao;

	@Override
	public String createUser(User user) {
		if (user == null)
			return null;
		String id = GenerateUUID.getUUID32();
		user.setId(id);
		user.setPasswd(Des.encryptBasedDes(user.getPasswd()));
		user.setApikey(GenerateUUID.getUUID32());
		user.setType(UserConstant.TYPE_NORMAL);
		userDao.insert(user);
		return id;
	}

	@Override
	public boolean modifyUser(User user) {
		if (user == null || StringUtils.isEmpty(user.getId()))
			return false;
		User userById = getUserById(user.getId());
		if (userById == null)
			return false;
		userById.setName(user.getName());
		userById.setEmail(user.getEmail());
		userById.setPasswd(Des.encryptBasedDes(user.getPasswd()));
		userById.setReadKey(Des.encryptBasedDes(user.getReadKey()));
		userById.setTelephone(user.getTelephone());
		userDao.update(userById);
		return true;
	}

	@Override
	public User getUserById(String id) {
		if (StringUtils.isEmpty(id))
			return null;
		return userDao.selectUserById(id);
	}

	@Override
	public User getUserByUsername(String username) {
		if (StringUtils.isEmpty(username))
			return null;
		return userDao.selectUserByUsername(username);
	}

	@Override
	public User getUserByApikey(String apikey) {
		if (StringUtils.isEmpty(apikey))
			return null;
		return userDao.selectUserByApikey(apikey);
	}
}
