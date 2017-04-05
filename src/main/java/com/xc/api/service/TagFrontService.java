package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface TagFrontService {
	public String createTag(String jsonString);

	public String modifyTag(String jsonString);

	public String getTag(String id);

	public String getTagsList(String name, Integer type, Integer page, Integer size, String sortKey, Integer sortType);

	public void removeTag(String id);

}
