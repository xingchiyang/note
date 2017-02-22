package com.xc.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@Entity
public class Note {
	@Id
	@GenericGenerator(name = "noteId", strategy = "uuid")
	@GeneratedValue(generator = "noteId")
	private String id;
	private String dirId;
	private String title; // 标题
	private String content; // 内容
	private Integer type; // 笔记类型，markdown、常规笔记
	private String path; // 笔记所在系统文件中的目录

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
