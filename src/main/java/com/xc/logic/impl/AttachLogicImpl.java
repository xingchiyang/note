package com.xc.logic.impl;

import com.xc.dao.AttachDao;
import com.xc.entity.Attach;
import com.xc.logic.AttachLogic;
import com.xc.util.Criterions;
import com.xc.util.GenerateUUID;
import com.xc.util.page.Pagination;
import com.xc.util.page.SortConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/04/07 0007.
 */
@Service
public class AttachLogicImpl implements AttachLogic {
	@Autowired
	private AttachDao attachDao;

	@Override
	public String createAttach(Attach attach) {
		if (attach == null)
			return null;
		String id = "";
		if (StringUtils.isEmpty(attach.getId())) {
			id = GenerateUUID.getUUID32();
		} else {
			id = attach.getId();
		}
		attach.setId(id);
		attach.setUploadTime(new Date());
		attachDao.insert(attach);
		return attach.getId();
	}

	@Override
	public boolean modifyAttach(Attach attach) {
		if (attach == null || StringUtils.isEmpty(attach.getId()))
			return false;
		Attach attachById = getAttachById(attach.getId());
		if (attachById == null)
			return false;
		attachById.setName(attach.getName());
		attachById.setSize(attach.getSize());
		attachById.setNoteId(attach.getNoteId());
		attachDao.update(attachById);
		return true;
	}

	@Override
	public Attach getAttachById(String id) {
		if (StringUtils.isEmpty(id))
			return null;
		return attachDao.selectAttachById(id);
	}

	@Override
	public List<Attach> getAttachByNoteId(String noteId) {
		if (StringUtils.isEmpty(noteId))
			return null;
		return attachDao.selectAttachByNoteId(noteId);
	}

	@Override
	public Pagination<Attach> getAttachsList(String name, String type, Integer page, Integer size, String sortKey,
			Integer sortType) {
		Criterions criterions = new Criterions();
		Criterions.Criteria criteria = criterions.createCriteria();
		if (!StringUtils.isEmpty(name)) {
			String param = "%" + name + "%";
			String nameClause = "name like ?";
			criteria.andCustom(nameClause, param);
		}
		if (!StringUtils.isEmpty(type)) {
			criteria.andColumnEqualTo("type", type);
		}
		Integer total = attachDao.countAttachsByCriterions(criterions);
		Pagination<Attach> p = new Pagination<Attach>(page, size, total);
		criterions.setStart(p.getFirstResult());
		criterions.setLimit(p.getPageSize());
		if (total > 0) {
			String orderClause = SortConvert.convert(sortKey, sortType, "upload_time", -1);
			criterions.setOrderByClause(orderClause);
			p.setData(attachDao.selectAttachsByCriterions(criterions));
		} else {
			p.setData(new ArrayList<>());
		}
		return p;
	}

	@Override
	public void removeAttach(String id) {
		if (StringUtils.isEmpty(id))
			return;
		attachDao.delete(id);
	}

	@Override
	public void removeAttachByNoteId(String noteId) {
		if (StringUtils.isEmpty(noteId))
			return;
		attachDao.deleteByNoteId(noteId);
	}
}
