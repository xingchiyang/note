package com.xc.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public class Note {
	private String id;

	private String dirId;

	private String title; // 标题

	private String content; // 内容

	private Integer type; // 笔记类型，markdown、常规笔记

	private String tags;

	private List<Attach> attach; // 供前端调用和显示用

	private Date createTime;

	private Date modifyTime;

	private Integer status; // 笔记的状态，是否已删除，常规

	private String userId;

	public Note() {
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDirId() {
		return dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Attach> getAttach() {
		return attach;
	}

	public void setAttach(List<Attach> attach) {
		this.attach = attach;
	}
}
