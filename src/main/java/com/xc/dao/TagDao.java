package com.xc.dao;

import com.xc.entity.Tag;
import com.xc.util.Criterions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/04/05 0005.
 */
public interface TagDao {
	public void insert(Tag tag);

	public void update(Tag tag);

	public Tag selectTagById(@Param("id") String id);

	public Integer countTagsByCriterions(Criterions criterions);

	public List<Tag> selectTagsByCriterions(Criterions criterions);

	public void delete(@Param("id") String id);

}
