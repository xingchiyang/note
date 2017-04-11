package com.xc.logic.impl;

import com.xc.dao.TagDao;
import com.xc.entity.Tag;
import com.xc.logic.TagLogic;
import com.xc.util.Criterions;
import com.xc.util.GenerateUUID;
import com.xc.util.SecurityContextHolder;
import com.xc.util.page.Pagination;
import com.xc.util.page.SortConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/04/05 0005.
 */
@Service
public class TagLogicImpl implements TagLogic {
	@Autowired
	private TagDao tagDao;

	@Override
	public String createTag(Tag tag) {
		if (tag == null)
			return null;
		String id = GenerateUUID.getUUID32();
		tag.setId(id);
		Date date = new Date();
		tag.setCreateTime(date);
		tag.setModifyTime(date);
		tag.setUserId(SecurityContextHolder.getUserId());
		tagDao.insert(tag);
		return id;
	}

	@Override
	public boolean modifyTag(Tag tag) {
		if (tag == null || StringUtils.isEmpty(tag.getId()))
			return false;
		Tag oldTag = getTagById(tag.getId());
		if (oldTag == null)
			return false;
		oldTag.setName(tag.getName());
		oldTag.setModifyTime(new Date());
		tagDao.update(oldTag);
		return true;
	}

	@Override
	public Tag getTagById(String id) {
		if (StringUtils.isEmpty(id))
			return null;
		Tag tag = tagDao.selectTagById(id);
		return tag;
	}

	@Override
	public Pagination<Tag> getTagsList(String name, Integer type, Integer page, Integer size, String sortKey,
			Integer sortType, String userId) {
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
		if (!StringUtils.isEmpty(userId)) {
			criteria.andColumnEqualTo("user_id", userId);
		}
		Integer total = tagDao.countTagsByCriterions(criterions);
		Pagination<Tag> p = new Pagination<Tag>(page, size, total);
		criterions.setStart(p.getFirstResult());
		criterions.setLimit(p.getPageSize());
		if (total > 0) {
			String orderClause = SortConvert.convert(sortKey, sortType, "modify_time", -1);
			criterions.setOrderByClause(orderClause);
			p.setData(tagDao.selectTagsByCriterions(criterions));
		} else {
			p.setData(new ArrayList<>());
		}
		return p;
	}

	@Override
	@Transactional
	public void removeTags(String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		String[] s = ids.split(",");
		for (String id : s) {
			tagDao.delete(id);
		}
	}
}
