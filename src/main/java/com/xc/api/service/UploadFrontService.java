package com.xc.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
public interface UploadFrontService {

	public Object uploadFile(HttpServletRequest request);

	public Object getImg(String id, HttpServletResponse res);

}
