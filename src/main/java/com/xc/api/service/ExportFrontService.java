package com.xc.api.service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/02/22 0022.
 * 笔记导出类
 */
public interface ExportFrontService {
	public Object export(String id, String format, HttpServletResponse response);
}
