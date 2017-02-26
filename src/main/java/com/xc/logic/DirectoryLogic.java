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

	public List<Directory> getDirsByParentId(String id);

	public void removeDir(String id);
}
