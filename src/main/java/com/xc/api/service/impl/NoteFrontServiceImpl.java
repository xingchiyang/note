package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.NoteFrontService;
import com.xc.constant.Constant;
import com.xc.entity.Note;
		import com.xc.logic.NoteLogic;
		import com.xc.util.RestReturnUtil;
		import com.xc.util.ValidateUtil;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping(value = "/api/v1/note", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class NoteFrontServiceImpl implements NoteFrontService {
	@Autowired
	private NoteLogic noteLogic;

	@PostMapping("/create")
	@Override
	public String createNote(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		Note note = JSON.parseObject(jsonString, Note.class);
		ValidateUtil.validateStrBlank(note.getTitle(), "笔记title不能为空");
		ValidateUtil.validateIntNull(note.getType(), "类型不能为空");
		return RestReturnUtil.toObject("id", noteLogic.createNote(note));
	}

	@PostMapping("/modify")
	@Override
	public String modifyNote(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		Note note = JSON.parseObject(jsonString, Note.class);
		ValidateUtil.validateStrBlank(note.getId(), "笔记id不能为空");
		ValidateUtil.validateStrBlank(note.getTitle(), "笔记title不能为空");
		if (noteLogic.modifyNote(note)) {
			return RestReturnUtil.toObject("id", note.getId());
		}
		return null;
	}
}
