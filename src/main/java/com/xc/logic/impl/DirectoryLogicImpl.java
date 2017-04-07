package com.xc.logic.impl;

import com.xc.constant.DirConstant;
import com.xc.dao.DirectoryDao;
import com.xc.entity.Directory;
import com.xc.logic.DirectoryLogic;
import com.xc.logic.NoteLogic;
import com.xc.util.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by yb on 2017/2/25 0025.
 */
@Service
public class DirectoryLogicImpl implements DirectoryLogic {
	@Autowired
	private DirectoryDao directoryDao;
	@Autowired
	private NoteLogic noteLogic;

	@Override
	public String createDir(Directory directory) {
		String id = GenerateUUID.getUUID32();
		directory.setId(id);
		Date date = new Date();
		directory.setCreateTime(date);
		directory.setModifyTime(date);
		directoryDao.insert(directory);
		return id;
	}

	@Override
	public boolean modifyDir(Directory directory) {
		// 判断是否存在
		Directory oldDir = getDirById(directory.getId());
		if (oldDir == null) {
			return false;
		}
		oldDir.setModifyTime(new Date());
		oldDir.setName(directory.getName());
		oldDir.setParentId(directory.getParentId());
		directoryDao.update(oldDir);
		return true;
	}

	@Override
	public Directory getDirById(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		return directoryDao.selectDirById(id);
	}

	@Override
	public List<Directory> getDirsByParentIdStatus(String id, Integer status) {
		return directoryDao.selectDirsByParentIdStatus(id, status);
	}

	@Override
	@Transactional
	public void removeDir(String id) {
		if (StringUtils.isEmpty(id)) {
			return;
		}
		// 删除子目录及子目录下面的笔记
		List<Directory> subDirs = getDirsByParentIdStatus(id, Integer.valueOf(DirConstant.STATUS_DELETED));
		if (subDirs != null && subDirs.size() > 0) {
			for (Directory subDir : subDirs) {
				removeDir(subDir.getId());
			}
		}
		// 删除目录下的笔记
		noteLogic.removeNotesByDirId(id);
		// 删除目录
		directoryDao.delete(id);
	}

	@Override
	@Transactional
	public void removeDirToRecycle(String id) {
		if (StringUtils.isEmpty(id)) {
			return;
		}
		// 只将目录放入回收站，子目录和笔记状态不变
		Directory dir = directoryDao.selectDirById(id);
		if (dir != null) {
			dir.setStatus(DirConstant.STATUS_DELETED);
			directoryDao.update(dir);
		}
	}

	@Override
	public void resumeDirFromRecycle(String id) {
		if (StringUtils.isEmpty(id)) {
			return;
		}
		Directory dir = directoryDao.selectDirById(id);
		if (dir != null) {
			dir.setStatus(DirConstant.STATUS_NORMAL);
			directoryDao.update(dir);
		}
	}

	@Override
	public List<Directory> getDirsByStatus(Integer status) {
		if (status == null) {
			return null;
		}
		return directoryDao.selectDirsByStatus(status);
	}
}
