package com.xc.api.service;

/**
 * Created by Administrator on 2017/02/27 0027.
 */
public interface FileFrontService {

	public String getFileByDirId(String id, String readKey);

	public String getFileInRecycle();

	public String clearAllFromRecycle();

	public String setReadKey(int fileType, String fileId);

	public String cancelReadKey(int fileType, String fileId, String readKey);

}
