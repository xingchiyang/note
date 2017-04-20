package com.xc.dao;

import com.xc.entity.Note;
import com.xc.util.Criterions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteDao {

	public void insert(Note note);

	public void update(Note note);

	public Note selectNoteById(@Param("id") String id);

	public Integer countNotesByCriterions(Criterions criterions);

	public List<Note> selectNotesByCriterions(Criterions criterions);

	public void delete(@Param("id") String id);

	public void clear(@Param("id") String id);

	public void deleteByDirId(@Param("dirId") String dirId);

	public List<Note> selectNotesByDirId(@Param("dirId") String dirId);

	public List<Note> selectNotesByDirIdStatusUserId(@Param("dirId") String dirId, @Param("status") List<Integer> status,
			@Param("userId") String userId);

	public List<Note> selectNotesByStatus(@Param("status") Integer status, @Param("userId") String userId);

}
