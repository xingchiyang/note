package com.xc.logic.impl;

import com.xc.constant.Constant;
import com.xc.constant.FileConstant;
import com.xc.dao.NoteDao;
import com.xc.entity.Attach;
import com.xc.entity.Directory;
import com.xc.entity.Note;
import com.xc.logic.AttachLogic;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.util.Criterions;
import com.xc.util.GenerateUUID;
import com.xc.util.SecurityContextHolder;
import com.xc.util.page.Pagination;
import com.xc.util.page.SortConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@Service
public class NoteLogicImpl implements NoteLogic {
	@Autowired
	private NoteDao noteDao;
	@Autowired
	private DirectoryLogic dirLogic;
	@Autowired
	private AttachLogic attachLogic;

	@Override
	@Transactional
	public String createNote(Note note) {
		Directory dir = dirLogic.getDirById(note.getDirId());
		if (dir == null) {
			note.setDirId(null); // 如果目录id
		}
		String id = GenerateUUID.getUUID32();
		note.setId(id);
		note.setStatus(FileConstant.STATUS_NORMAL);
		note.setDirId(note.getDirId());
		Date now = new Date();
		note.setCreateTime(now);
		note.setModifyTime(now);
		note.setUserId(SecurityContextHolder.getUserId());
		noteDao.insert(note);

		updateAttach(note);

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
		oldNote.setModifyTime(new Date());
		oldNote.setStatus(note.getStatus());
		noteDao.update(oldNote);

		updateAttach(note);

		return true;
	}

	// 更新插件表
	private void updateAttach(Note note) {
		List<Attach> attach = note.getAttach();
		if (attach != null && attach.size() > 0) {
			for (int i = 0; i < attach.size(); i++) {
				Attach att = attach.get(i);
				Attach realAttach = attachLogic.getAttachById(att.getId());
				realAttach.setNoteId(note.getId());
				attachLogic.modifyAttach(realAttach);
			}
		}
	}

	@Override
	public Note getNoteById(String id) {
		if (StringUtils.isEmpty(id))
			return null;
		Note note = noteDao.selectNoteById(id);
		List<Attach> attachList = attachLogic.getAttachByNoteId(id);
		note.setAttach(attachList);
		return note;
	}

	@Override
	public Pagination<Note> getNotesList(String name, String dirId, Integer type, Integer status, Integer page,
			Integer size, String sortKey, Integer sortType, String userId) {
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
		if (!StringUtils.isEmpty(userId)) {
			criteria.andColumnEqualTo("user_id", userId);
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

			// TODO 删除本地附件
			List<Attach> attachByNoteId = attachLogic.getAttachByNoteId(id);
			if (attachByNoteId != null && attachByNoteId.size() > 0) {
				File file = null;
				for (Attach att : attachByNoteId) {
					file = new File(Constant.FILE_DIR + "/" + att.getId());
					if (file.exists()) {
						file.delete();
					}
				}
			}
			// 删除相应附件表
			attachLogic.removeAttachByNoteId(id);
		}
	}

	@Override
	public void resumeNote(String id) {
		if (StringUtils.isEmpty(id))
			return;
		Note note = noteDao.selectNoteById(id);
		if (note != null) {
			note.setStatus(FileConstant.STATUS_NORMAL);
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
		return noteDao.selectNotesByDirId(dirId);
	}

	@Override
	public List<Note> getNoteListByDirIdStatusUserId(String dirId, List<Integer> status, String userId) {
		return noteDao.selectNotesByDirIdStatusUserId(dirId, status, userId);
	}

	@Override
	public List<Note> getNotesByStatusUserId(Integer status, String userId) {
		if (status == null)
			return null;
		return noteDao.selectNotesByStatus(status, userId);
	}

	@Override
	public List<Note> getNotesBySearchKey(String key, List<Integer> status, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("status", status);
		map.put("userId", userId);
		return noteDao.selectNotesByMap(map);
	}

}
