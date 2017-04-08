package com.xc.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/04/07 0007.
 */
public class Attach {
	private String id;
	private String name;
	private String type;
	private Date uploadTime;
	private long size;
	private String noteId;
	private Note note;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
}
