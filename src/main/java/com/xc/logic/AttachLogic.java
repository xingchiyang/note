package com.xc.logic;

import com.xc.entity.Attach;
import com.xc.util.page.Pagination;

import java.util.List;

/**
 * Created by yb on 2017/2/25 0025.
 */
public interface AttachLogic {
	public String createAttach(Attach attach);

	public boolean modifyAttach(Attach attach);

	public Attach getAttachById(String id);

	public List<Attach> getAttachByNoteId(String noteId);

	public Pagination<Attach> getAttachsList(String name, Integer page, Integer size, String sortKey, Integer sortType);

	public void removeAttach(String id);

	public void removeAttachByNoteId(String noteId);

}
