package com.xc.api.service.impl;

import com.xc.api.service.NoteFrontService;
import com.xc.constant.NoteConstant;
import com.xc.entity.Note;
import com.xc.logic.NoteLogic;
import com.xc.util.GenerateUUID;
import com.xc.util.RestReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping("/api/v1/note")
public class NoteFrontServiceImpl implements NoteFrontService {
	@Autowired
	private NoteLogic noteLogic;

	@PostMapping("/create")
	public String createNote() {
		Note note = new Note();
		String id = GenerateUUID.getUUID32();
		note.setId(id);
		note.setTitle("title");
		note.setContent("content");
		note.setType(NoteConstant.TYPE_NORMAL);
		return RestReturnUtil.toObject("id", noteLogic.createNote(note));
	}
}
