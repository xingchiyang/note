package com.xc.dao;

import com.xc.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/04/07 0007.
 */
public interface UserDao {

	public void insert(User user);

	public void update(User user);

	public User selectUserById(@Param("id") String id);

	public User selectUserByUsername(@Param("username") String username);

}
