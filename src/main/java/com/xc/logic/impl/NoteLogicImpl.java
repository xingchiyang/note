package com.xc.logic.impl;

import com.xc.dao.NoteDao;
import com.xc.entity.Note;
import com.xc.logic.NoteLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@Service
public class NoteLogicImpl implements NoteLogic {
	@Autowired
	private NoteDao noteDao;

	public String createNote(Note note) {
		noteDao.save(note);
		return note.getId();
	}


}
