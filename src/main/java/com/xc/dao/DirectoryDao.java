package com.xc.dao;

import com.xc.entity.Directory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface DirectoryDao {
	public void insert(Directory directory);

	public void update(Directory directory);

	public Directory selectDirById(@Param("id") String id);

	public List<Directory> selectDirsByParentId(@Param("parentId") String parentId);

	public void delete(@Param("id") String id);
}
