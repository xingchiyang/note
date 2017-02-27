package com.xc.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/02/27 0027.
 */
public class FileInfo {
	private List<Directory> dirs;
	private List<Note> notes;
	private long totalSize;

	public List<Directory> getDirs() {
		return dirs;
	}

	public void setDirs(List<Directory> dirs) {
		this.dirs = dirs;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
}
