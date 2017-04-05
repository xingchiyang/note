package com.xc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.xc.api.service.TagFrontService;
import com.xc.constant.Constant;
import com.xc.entity.Tag;
import com.xc.logic.TagLogic;
import com.xc.util.JsonUtil;
import com.xc.util.RestReturnUtil;
import com.xc.util.ValidateUtil;
import com.xc.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/04/05 0005.
 */
@RestController
@RequestMapping(value = "/api/v1/tag", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class TagFrontServiceImpl implements TagFrontService {
	@Autowired
	private TagLogic tagLogic;

	@PostMapping("/create")
	@Override
	public String createTag(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		Tag tag = JSON.parseObject(jsonString, Tag.class);
		ValidateUtil.validateStrBlank(tag.getName(), "标签name不能为空");
		ValidateUtil.validateIntNull(tag.getType(), "标签type不能为空");
		Pagination<Tag> tagsList = tagLogic.getTagsList(tag.getName(), null, null, null, null, null);
		ValidateUtil.validateTrue(tagsList.getData().size() <= 0, "存在同名标签");
		return RestReturnUtil.toObject("id", tagLogic.createTag(tag));
	}

	@PostMapping("/modify")
	@Override
	public String modifyTag(@RequestBody String jsonString) {
		ValidateUtil.validateStrBlank(jsonString, "请求参数为空");
		Tag tag = JSON.parseObject(jsonString, Tag.class);
		ValidateUtil.validateStrBlank(tag.getId(), "标签id不能为空");
		ValidateUtil.validateStrBlank(tag.getName(), "标签name不能为空");
		Pagination<Tag> tagsList = tagLogic.getTagsList(tag.getName(), null, null, null, null, null);
		if (tagsList.getData().size() > 0) {
			ValidateUtil.validateTrue(tagsList.getData().get(0).getId().equals(tag.getId()), "存在同名标签");
		}
		if (tagLogic.modifyTag(tag)) {
			return RestReturnUtil.toObject("id", tag.getId());
		}
		return null;
	}

	@GetMapping(value = "/get/{id}", consumes = "*/*")
	@Override
	public String getTag(@PathVariable String id) {
		Tag tag = tagLogic.getTagById(id);
		return JsonUtil.includePropToJson(tag);
	}

	@GetMapping(value = "/query", consumes = "*/*")
	@Override
	public String getTagsList(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "sortType", required = false) Integer sortType) {
		Pagination<Tag> tagsList = tagLogic.getTagsList(name, type, page, size, sortKey, sortType);
		return JsonUtil.includePropToJson(tagsList.formate());
	}

	@GetMapping(value = "/delete/{ids}", consumes = "*/*")
	@Override
	public void removeTag(@PathVariable String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		tagLogic.removeTags(ids);
	}
}
