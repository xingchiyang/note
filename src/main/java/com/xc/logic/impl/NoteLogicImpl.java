package com.xc.logic.impl;

import com.xc.constant.NoteConstant;
import com.xc.dao.NoteDao;
import com.xc.entity.Note;
import com.xc.logic.NoteLogic;
import com.xc.util.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@Service
public class NoteLogicImpl implements NoteLogic {
	@Autowired
	private NoteDao noteDao;

	public String createNote(Note note) {
		String id = GenerateUUID.getUUID32();
		note.setId(id);
		note.setStatus(NoteConstant.STATUS_NORMAL);
		Date now = new Date();
		note.setCreateTime(now);
		note.setModifyTime(now);
		noteDao.save(note);
		return id;
	}

	@Override
	public boolean modifyNote(Note note) {
		Note oldNote = getNoteById(note.getId());
		if (oldNote == null) {
			return false;
		}
		oldNote.setContent(note.getContent());
		oldNote.setTitle(note.getTitle());
		oldNote.setModifyTime(new Date());
		noteDao.save(oldNote);
		return true;
	}

	@Override
	public Note getNoteById(String id) {
		return noteDao.findOne(id);
	}

	@Override
	public List<Note> getNotesList(String name, String dirId, Integer type, Integer page, Integer size, String sortKey,
			Integer sortType) {
		return null;
	}

	@Override
	@Transactional
	public void removeNoteByid(String id) {
		noteDao.delete(id);
	}

}
