package com.xc.logic;

import com.xc.entity.User;

/**
 * Created by yb on 2017/2/25 0025.
 */
public interface UserLogic {

	public String createUser(User user);

	public boolean modifyUser(User user);

	public User getUserById(String id);

	public User getUserByUsername(String username);

}
