package com.xc.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xc.api.service.FileFrontService;
import com.xc.constant.Constant;
import com.xc.constant.NoteConstant;
import com.xc.entity.Directory;
import com.xc.entity.Note;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.util.JsonUtil;
import com.xc.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
		// 兼容jstree传过来的根节点id
		if ("#".equals(id)) {
			id = null;
		}
		Directory directory = dirLogic.getDirById(id);
		List<Directory> dirs = dirLogic.getDirsByParentId(id);
		Pagination<Note> pagination = noteLogic
				.getNotesList(null, id, null, 1, NoteConstant.STATUS_NORMAL, Integer.MAX_VALUE, null, null);
		List<Note> notes = pagination.getData();

		JSONArray ret = new JSONArray();
		if (dirs != null && dirs.size() > 0) {
			for (Directory dir : dirs) {
				JSONObject object = new JSONObject();
				object.put("id", dir.getId());
				object.put("text", dir.getName());
				object.put("icon", "../../images/dir.png");
				object.put("children", true);
				JSONObject aAttr = new JSONObject();
				aAttr.put("isDir", true);
				object.put("a_attr", aAttr);
				ret.add(object);
			}
		}
		if (notes != null && notes.size() > 0) {
			for (Note note : notes) {
				JSONObject object = new JSONObject();
				object.put("id", note.getId());
				object.put("text", note.getTitle());
				object.put("icon", "../../images/file.png");
				JSONObject aAttr = new JSONObject();
				aAttr.put("isDir", false);
				object.put("a_attr", aAttr);
				ret.add(object);
			}
		}
		JSONObject root = new JSONObject();
		root.put("id", id);
		root.put("text", id == null ? "我的文件" : (directory != null ? directory.getName() : ""));
		root.put("icon", "../../images/dir.png");
		JSONObject state = new JSONObject();
		state.put("opened", true);
		root.put("state", state);
		root.put("children", ret);
		JSONObject aAttr = new JSONObject();
		aAttr.put("isDir", true);
		root.put("a_attr", aAttr);
		return JsonUtil.includePropToJson(root);
	}
}
