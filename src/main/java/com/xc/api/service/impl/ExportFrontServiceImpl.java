package com.xc.api.service.impl;

import com.xc.api.service.ExportFrontService;
import com.xc.constant.Constant;
import com.xc.entity.Note;
import com.xc.exception.NoteException;
import com.xc.exception.NoteExpCode;
import com.xc.logic.NoteLogic;
import com.xc.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping(value = "/api/v1/file", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class ExportFrontServiceImpl implements ExportFrontService {
	@Autowired
	private NoteLogic noteLogic;

	@Override
	@GetMapping(value = "/export", consumes = "*/*")
	public Object export(@RequestParam String id, @RequestParam String format, HttpServletResponse response) {
		Note note = noteLogic.getNoteById(id);
		if (note == null) {
			return JsonUtil.includePropToJson(null);
		}
		ServletOutputStream outputStream = null;
		try {
			response.setHeader("Content-Disposition",
					"attachment; filename*=UTF-8''" + URLEncoder.encode(note.getTitle() + "." + format, "UTF-8"));
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			outputStream = response.getOutputStream();
			outputStream.write(note.getContent().getBytes());
		} catch (IOException e) {
			throw new NoteException(NoteExpCode.EXP_CODE_SERVICE, "服务异常");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return JsonUtil.includePropToJson(null);
	}
}
