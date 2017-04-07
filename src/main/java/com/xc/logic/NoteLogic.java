package com.xc.logic;

import com.xc.entity.Note;
import com.xc.util.page.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteLogic {
	public String createNote(Note note);

	public boolean modifyNote(Note note);

	public Note getNoteById(String id);

	public Pagination<Note> getNotesList(String name, String dirId, Integer type, Integer status, Integer page, Integer size, String sortKey,
			Integer sortType);

	public void removeNotes(String ids);

	public void clearNotes(String ids);

	public void resumeNote(String id);

	public void removeNotesByDirId(String dirId);

	public List<Note> getNoteListByDirId(String dirId);

	public List<Note> getNotesByStatus(Integer status);
}
