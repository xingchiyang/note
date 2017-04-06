package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface DirectoryFrontService {

	public String createDir(String jsonString);

	public void renameDir(String id, String newName);

	public void moveDir(String id, String parentId);

	public String getDirById(String id);

	public String getDirsByParentId(String parentId);

	public void removeDir(String id);

	public void removeDirToRecycle(String id);
}
