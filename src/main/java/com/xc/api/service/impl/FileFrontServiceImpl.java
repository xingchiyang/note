package com.xc.api.service.impl;

import com.xc.api.service.FileFrontService;
import com.xc.constant.Constant;
import com.xc.entity.FileInfo;
import com.xc.entity.Note;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.util.JsonUtil;
import com.xc.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/02/27 0027.
 */
@RestController
@RequestMapping(value = "/api/v1/file", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class FileFrontServiceImpl implements FileFrontService {
	@Autowired
	private DirectoryLogic dirLogic;
	@Autowired
	private NoteLogic noteLogic;

	@Override
	@GetMapping(value = "/query", consumes = "*/*")
	public String getFileByDirId(@RequestParam(value = "id", required = false) String id) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setDirs(dirLogic.getDirsByParentId(id));
		Pagination<Note> notes = noteLogic.getNotesList(null, id, null, 1, Integer.MAX_VALUE, null, null);
		fileInfo.setNotes(notes.getData());
		fileInfo.setTotalSize(fileInfo.getDirs().size() + fileInfo.getNotes().size());
		return JsonUtil.includePropToJson(fileInfo);
	}
}
