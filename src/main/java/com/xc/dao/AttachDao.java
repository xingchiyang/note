package com.xc.dao;

import com.xc.entity.Attach;
import com.xc.util.Criterions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/04/07 0007.
 */
public interface AttachDao {

	public void insert(Attach attach);

	public void update(Attach attach);

	public void delete(@Param("id") String id);

	public void deleteByNoteId(@Param("noteId") String noteId);

	public Attach selectAttachById(@Param("id") String id);

	public List<Attach> selectAttachByNoteId(@Param("noteId") String noteId);

	public Integer countAttachsByCriterions(Criterions criterions);

	public List<Attach> selectAttachsByCriterions(Criterions criterions);

}
