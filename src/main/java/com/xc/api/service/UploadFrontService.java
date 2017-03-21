package com.xc.api.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
public interface UploadFrontService {
	public Object uploadImg(HttpServletRequest request);

	public Object getImg(String id);

}
