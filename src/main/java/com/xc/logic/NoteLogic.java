package com.xc.logic;

import com.xc.entity.Note;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteLogic {
	public String createNote(Note note);

	public boolean modifyNote(Note note);

	public Note getNoteById(String id);
}
