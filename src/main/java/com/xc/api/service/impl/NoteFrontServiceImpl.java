package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.NoteFrontService;
import com.xc.constant.Constant;
import com.xc.entity.Note;
import com.xc.logic.NoteLogic;
import com.xc.util.JsonUtil;
import com.xc.util.RestReturnUtil;
import com.xc.util.SecurityContextHolder;
import com.xc.util.ValidateUtil;
import com.xc.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
		note.setUserId(SecurityContextHolder.getUserId());
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

	@GetMapping(value = "/get/{id}", consumes = "*/*")
	@Override
	public String getNote(@PathVariable String id) {
		Note note = noteLogic.getNoteById(id);
		return JsonUtil.includePropToJson(note);
	}

	@GetMapping(value = "/query", consumes = "*/*")
	@Override
	public String getNotesList(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "dirId", required = false) String dirId,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "sortType", required = false) Integer sortType) {
		Pagination<Note> notesList = noteLogic.getNotesList(name, dirId, type, status, page, size, sortKey, sortType, SecurityContextHolder.getUserId());
		return JsonUtil.includePropToJson(notesList.formate());
	}

	@GetMapping(value = "/delete/{ids}", consumes = "*/*")
	@Override
	public void removeNote(@PathVariable String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		noteLogic.removeNotes(ids);
	}

	@GetMapping(value = "/clear/{ids}", consumes = "*/*")
	@Override
	public void clearNotes(@PathVariable String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		noteLogic.clearNotes(ids);
	}

	@GetMapping(value = "/resume/{id}", consumes = "*/*")
	@Override
	public void resumeNote(@PathVariable String id) {
		if (StringUtils.isEmpty(id))
			return;
		noteLogic.resumeNote(id);
	}

}
