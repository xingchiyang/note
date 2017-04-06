package com.xc.logic;

import com.xc.entity.Directory;

import java.util.List;

/**
 * Created by yb on 2017/2/25 0025.
 */
public interface DirectoryLogic {
	public String createDir(Directory directory);

	public boolean modifyDir(Directory directory);

	public Directory getDirById(String id);

	public List<Directory> getDirsByParentIdStatus(String id, Integer status);

	public void removeDir(String id);

	public void removeDirToRecycle(String id);

	public List<Directory> getDirsByStatus(Integer status);
}
