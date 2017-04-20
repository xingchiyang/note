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

	public List<Directory> selectDirsByParentIdStatus(@Param("parentId") String parentId, @Param("status") List<Integer> status, @Param("userId") String userId);

	public void delete(@Param("id") String id);

	public List<Directory> selectDirsByStatus(@Param("status") Integer status, @Param("userId") String userId);
}
