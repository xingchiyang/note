package com.xc.logic.impl;

import com.xc.constant.NoteConstant;
import com.xc.dao.NoteDao;
import com.xc.entity.Directory;
import com.xc.entity.Note;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.util.Criterions;
import com.xc.util.GenerateUUID;
import com.xc.util.page.Pagination;
import com.xc.util.page.SortConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@Service
public class NoteLogicImpl implements NoteLogic {
	@Autowired
	private NoteDao noteDao;
	@Autowired
	private DirectoryLogic dirLogic;

	public String createNote(Note note) {
		Directory dir = dirLogic.getDirById(note.getDirId());
		if (dir == null) {
			note.setDirId(null); // 如果目录id
		}
		String id = GenerateUUID.getUUID32();
		note.setId(id);
		note.setStatus(NoteConstant.STATUS_NORMAL);
		note.setDirId(note.getDirId());
		Date now = new Date();
		note.setCreateTime(now);
		note.setModifyTime(now);
		noteDao.insert(note);
		return id;
	}

	@Override
	public boolean modifyNote(Note note) {
		Note oldNote = getNoteById(note.getId());
		if (oldNote == null) {
			return false;
		}
		oldNote.setContent(note.getContent());
		oldNote.setTitle(note.getTitle());
		oldNote.setTags(note.getTags());
		oldNote.setAttach(note.getAttach());
		oldNote.setModifyTime(new Date());
		noteDao.update(oldNote);
		return true;
	}

	@Override
	public Note getNoteById(String id) {
		return noteDao.selectNoteById(id);
	}

	@Override
	public Pagination<Note> getNotesList(String name, String dirId, Integer type, Integer status, Integer page,
			Integer size, String sortKey, Integer sortType) {
		Criterions criterions = new Criterions();
		Criterions.Criteria criteria = criterions.createCriteria();
		if (!StringUtils.isEmpty(name)) {
			String param = "%" + name + "%";
			String nameClause = "name like ?";
			criteria.andCustom(nameClause, param);
		}
		if (!StringUtils.isEmpty(dirId)) {
			criteria.andColumnEqualTo("dir_id", dirId);
		} else {
			criteria.andColumnIsNull("dir_id");
		}
		if (!StringUtils.isEmpty(type)) {
			criteria.andColumnEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(status)) {
			criteria.andColumnEqualTo("status", status);
		}
		Integer total = noteDao.countNotesByCriterions(criterions);
		Pagination<Note> p = new Pagination<Note>(page, size, total);
		criterions.setStart(p.getFirstResult());
		criterions.setLimit(p.getPageSize());
		if (total > 0) {
			String orderClause = SortConvert.convert(sortKey, sortType, "create_time", -1);
			criterions.setOrderByClause(orderClause);
			p.setData(noteDao.selectNotesByCriterions(criterions));
		} else {
			p.setData(new ArrayList<>());
		}
		return p;
	}

	@Override
	@Transactional
	public void removeNotes(String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		for (String id : ids.split(",")) {
			noteDao.delete(id);
		}
	}

	@Override
	@Transactional
	public void clearNotes(String ids) {
		if (StringUtils.isEmpty(ids))
			return;
		for (String id : ids.split(",")) {
			noteDao.clear(id);
		}

	}

	@Override
	public void resumeNote(String id) {
		if (StringUtils.isEmpty(id))
			return;
		Note note = noteDao.selectNoteById(id);
		if (note != null) {
			note.setStatus(NoteConstant.STATUS_NORMAL);
			noteDao.update(note);
		}
	}

	@Override
	@Transactional
	public void removeNotesByDirId(String dirId) {
		if (dirId == null)
			return;
		noteDao.deleteByDirId(dirId);
	}

	@Override
	public List<Note> getNoteListByDirId(String dirId) {
		if (StringUtils.isEmpty(dirId))
			return null;
		return noteDao.selectNotesByDirId(dirId);
	}

	@Override
	public List<Note> getNotesByStatus(Integer status) {
		if (status == null)
			return null;
		return noteDao.selectNotesByStatus(status);
	}

}
