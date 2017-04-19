package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xc.api.service.FileFrontService;
import com.xc.constant.Constant;
import com.xc.constant.FileConstant;
import com.xc.entity.Directory;
import com.xc.entity.Note;
import com.xc.entity.User;
import com.xc.exception.NoteException;
import com.xc.exception.NoteExpCode;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.logic.UserLogic;
import com.xc.util.Des;
import com.xc.util.JsonUtil;
import com.xc.util.SecurityContextHolder;
import com.xc.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private UserLogic userLogic;

	@Override
	@PostMapping(value = "/query", consumes = "*/*")
	public String getFileByDirId(@RequestBody String jsonStr) {
		String id = getRequestParamByKey(jsonStr, "id");
		// 兼容jstree传过来的根节点id
		if (StringUtils.isEmpty(id)) {
			id = null;
		}
		String readKeyStr = getRequestParamByKey(jsonStr, "readKey");
		Directory directory = dirLogic.getDirById(id);
		List<Directory> dirs = null;
		List<Note> notes = null;
		if (directory != null && FileConstant.STATUS_ENCRYPTED == directory.getStatus()) {
			if (readKeyStr == null) {
				dirs = null;
				notes = null;
			} else {
				if (!Des.encryptBasedDes(readKeyStr).equals(getUserReadKey())) {
					throw new NoteException(NoteExpCode.EXP_CODE_PARAM, "密码错误");
				} else {
					dirs = dirLogic.getDirsByParentIdStatusUserId(id, Integer.valueOf(FileConstant.STATUS_NORMAL),
							SecurityContextHolder.getUserId());
					notes = noteLogic.getNoteListByDirId(id);
				}
			}
		} else {
			dirs = dirLogic.getDirsByParentIdStatusUserId(id, Integer.valueOf(FileConstant.STATUS_NORMAL),
					SecurityContextHolder.getUserId());
			notes = noteLogic.getNoteListByDirId(id);
		}

		JSONArray ret = new JSONArray();
		if (dirs != null && dirs.size() > 0) {
			for (Directory dir : dirs) {
				JSONObject object = new JSONObject();
				Directory dirById = dirLogic.getDirById(dir.getId());
				object.put("id", dir.getId());
				object.put("text", dir.getName());
				if (FileConstant.STATUS_ENCRYPTED == dirById.getStatus()) {
					object.put("icon", "../../images/dirLocked.png");
				} else {
					object.put("icon", "../../images/dir.png");
				}
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
				if (FileConstant.STATUS_ENCRYPTED == note.getStatus()) {
					object.put("icon", "../../images/fileLocked.png");
				} else {
					object.put("icon", "../../images/file.png");
				}
				JSONObject aAttr = new JSONObject();
				aAttr.put("isDir", false);
				object.put("a_attr", aAttr);
				ret.add(object);
			}
		}
		JSONObject root = new JSONObject();
		root.put("id", id);
		root.put("text", id == null ? "我的文件" : (directory != null ? directory.getName() : ""));
		if (directory != null && FileConstant.STATUS_ENCRYPTED == directory.getStatus()) {
			root.put("icon", "../../images/dirLocked.png");
		} else {
			root.put("icon", "../../images/dir.png");
		}
		JSONObject state = new JSONObject();
		state.put("opened", true);
		root.put("state", state);
		root.put("children", ret);
		JSONObject aAttr = new JSONObject();
		aAttr.put("isDir", true);
		root.put("a_attr", aAttr);
		return JsonUtil.includePropToJson(root);
	}

	private String getRequestParamByKey(String jsonStr, String key) {
		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		String[] split = jsonStr.split(";");
		for (String s : split) {
			String[] split1 = s.split("=", 2);
			if (split1[0].equals(key)) {
				return split1[1];
			}
		}
		return null;
	}

	private String getUserReadKey() {
		User user = userLogic.getUserById(SecurityContextHolder.getUserId());
		return user.getReadKey();
	}

	@Override
	@GetMapping(value = "/recycle/query", consumes = "*/*")
	public String getFileInRecycle() {
		JSONObject ret = new JSONObject();
		JSONArray dirs = new JSONArray();
		JSONArray notes = new JSONArray();
		ret.put("dirs", dirs);
		ret.put("notes", notes);
		List<Directory> dirsByStatus = dirLogic
				.getDirsByStatusUserId(FileConstant.STATUS_DELETED, SecurityContextHolder.getUserId());
		if (dirsByStatus != null && dirsByStatus.size() > 0) {
			for (Directory dir : dirsByStatus) {
				dirs.add(JSON.toJSON(dir));
			}
		}
		List<Note> notesByStatus = noteLogic
				.getNotesByStatusUserId(FileConstant.STATUS_DELETED, SecurityContextHolder.getUserId());
		if (notesByStatus != null && notesByStatus.size() > 0) {
			for (Note note : notesByStatus) {
				notes.add(JSON.toJSON(note));
			}
		}
		return JsonUtil.includePropToJson(ret);
	}

	@GetMapping(value = "/recycle/empty", consumes = "*/*")
	@Override
	public String clearAllFromRecycle() {
		List<Directory> dirsByStatus = dirLogic
				.getDirsByStatusUserId(FileConstant.STATUS_DELETED, SecurityContextHolder.getUserId());
		if (dirsByStatus != null && dirsByStatus.size() > 0) {
			for (Directory dir : dirsByStatus) {
				dirLogic.removeDir(dir.getId(), SecurityContextHolder.getUserId());
			}
		}
		List<Note> notesByStatus = noteLogic
				.getNotesByStatusUserId(FileConstant.STATUS_DELETED, SecurityContextHolder.getUserId());
		if (notesByStatus != null && notesByStatus.size() > 0) {
			for (Note note : notesByStatus) {
				noteLogic.clearNotes(note.getId());
			}
		}
		return JsonUtil.includePropToJson(null);
	}

	@Override
	@GetMapping(value = "/readKey/set", consumes = "*/*")
	public String setReadKey(@RequestParam("fileType") int fileType, @RequestParam("fileId") String fileId) {
		ValidateUtil.validateStrBlank(fileId, "文件id为空");
		if (FileConstant.FILE_TYPE_NOTE == fileType) {
			Note note = noteLogic.getNoteById(fileId);
			note.setStatus(FileConstant.STATUS_ENCRYPTED);
			noteLogic.modifyNote(note);
		} else if (FileConstant.FILE_TYPE_DIR == fileType) {
			Directory dir = dirLogic.getDirById(fileId);
			dir.setStatus(FileConstant.STATUS_ENCRYPTED);
			dirLogic.modifyDir(dir);
		}
		return JsonUtil.includePropToJson(null);
	}

	@Override
	@PostMapping(value = "/readKey/cancel", consumes = "*/*")
	public String cancelReadKey(@RequestParam("fileType") int fileType, @RequestParam("fileId") String fileId,
			@RequestBody String readKey) {
		ValidateUtil.validateStrBlank(fileId, "文件id为空");
		ValidateUtil.validateStrBlank(readKey, "阅读密码为空");
		JSONObject readKeyObj = JSON.parseObject(readKey);
		String readKeyStr = readKeyObj.getString("readKey");
		if (!Des.encryptBasedDes(readKeyStr).equals(getUserReadKey())) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, "密码错误");
		}
		if (FileConstant.FILE_TYPE_NOTE == fileType) {
			Note note = noteLogic.getNoteById(fileId);
			note.setStatus(FileConstant.STATUS_NORMAL);
			noteLogic.modifyNote(note);
		} else if (FileConstant.FILE_TYPE_DIR == fileType) {
			Directory dir = dirLogic.getDirById(fileId);
			dir.setStatus(FileConstant.STATUS_NORMAL);
			dirLogic.modifyDir(dir);
		}
		return JsonUtil.includePropToJson(null);
	}
}
