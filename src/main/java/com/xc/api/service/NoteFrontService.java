package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteFrontService {
	public String createNote(String jsonString);

	public String modifyNote(String jsonString);

	public String getNote(String id);

	public String getNotesList(String name, String dirId, Integer type, Integer status, Integer page, Integer size, String sortKey,
			Integer sortType);

	public void removeNote(String id);

	public void clearNotes(String ids);

	public void resumeNote(String id);


}
