package com.xc.logic;

import com.xc.entity.Tag;
import com.xc.util.page.Pagination;

/**
 * Created by yb on 2017/2/25 0025.
 */
public interface TagLogic {
	public String createTag(Tag tag);

	public boolean modifyTag(Tag tag);

	public Tag getTagById(String id);

	public Pagination<Tag> getTagsList(String name, Integer type, Integer page, Integer size, String sortKey, Integer sortType, String userId);

	public void removeTags(String ids);
}
