package com.xc.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
public interface AttachFrontService {

	public Object uploadFile(HttpServletRequest request);

	public Object getFile(String fileKey, String fileName, HttpServletResponse res);

	public Object removeFile(String id);

}