package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.DirectoryFrontService;
import com.xc.constant.Constant;
import com.xc.constant.DirConstant;
import com.xc.entity.Directory;
import com.xc.logic.DirectoryLogic;
import com.xc.util.JsonUtil;
import com.xc.util.RestReturnUtil;
import com.xc.util.SecurityContextHolder;
import com.xc.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping(value = "/api/v1/dir", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)

public class DirectoryFrontServiceImpl implements DirectoryFrontService {
	@Autowired
	private DirectoryLogic directoryLogic;

	@Override
	@PostMapping("/create")
	public String createDir(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		Directory directory = JSON.parseObject(jsonString, Directory.class);
		ValidateUtil.validateStrBlank(directory.getName(), "目录名称为空");
		directory.setStatus(DirConstant.STATUS_NORMAL);
		directory.setUserId(SecurityContextHolder.getUserId());
		String id = directoryLogic.createDir(directory);
		return RestReturnUtil.toObject("id", id);
	}

	@Override
	@GetMapping(value = "/rename", consumes = "*/*")
	public void renameDir(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "newName", required = true) String newName) {
		ValidateUtil.validateStrBlank(id, "目录id不能为空");
		ValidateUtil.validateStrBlank(newName, "目录名称不能为空");
		Directory directory = new Directory();
		directory.setId(id);
		directory.setName(newName);
		directoryLogic.modifyDir(directory);
	}

	@Override
	@GetMapping(value = "/move", consumes = "*/*")
	public void moveDir(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "parentId", required = false) String parentId) {
		ValidateUtil.validateStrBlank(id, "目录id不能为空");
		Directory directory = new Directory();
		directory.setId(id);
		directory.setParentId(parentId);
		directoryLogic.modifyDir(directory);
	}

	@Override
	@GetMapping(value = "/get/{id}", consumes = "*/*")
	public String getDirById(@PathVariable String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Directory dir = directoryLogic.getDirById(id);
		return JsonUtil.includePropToJson(dir);
	}

	@Override
	@GetMapping(value = "query", consumes = "*/*")
	public String getDirsByParentId(@RequestParam(value = "parentId", required = false) String parentId) {
		List<Directory> dirs = directoryLogic
				.getDirsByParentIdStatusUserId(parentId, Integer.valueOf(DirConstant.STATUS_NORMAL),
						SecurityContextHolder.getUserId());
		return JsonUtil.includePropToJson(dirs);
	}

	@Override
	@GetMapping(value = "clear/{id}", consumes = "*/*")
	public void removeDir(@PathVariable String id) {
		directoryLogic.removeDir(id, SecurityContextHolder.getUserId());
	}

	@GetMapping(value = "remove/{id}", consumes = "*/*")
	@Override
	public void removeDirToRecycle(@PathVariable String id) {
		directoryLogic.removeDirToRecycle(id);
	}

	@GetMapping(value = "resume/{id}", consumes = "*/*")
	@Override
	public void resumeDirFromRecycle(@PathVariable String id) {
		directoryLogic.resumeDirFromRecycle(id);
	}
}
