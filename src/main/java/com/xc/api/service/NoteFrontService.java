package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface NoteFrontService {
	public String createNote(String jsonString);

	public String modifyNote(String jsonString);

	public String getNote(String id);

	public String getNotesList(String name, String dirId, Integer type, Integer page, Integer size, String sortKey,
			String sortType);

	public void removeNote(String id);
}
