package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/23 0023.
 */
public interface UserFrontService {

	public String createUser(String jsonString);

	public String modifyUser(String jsonString);

	public String getUser();

	public String changePasswd(String jsonString);

	public String checkReadKey(String jsonString);

}
