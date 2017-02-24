package com.xc.dao;

import com.xc.entity.Note;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteDao {

	public void insert(Note note);

	public void update(Note note);

	public Note selectNoteById(@Param("id") String id);
}
