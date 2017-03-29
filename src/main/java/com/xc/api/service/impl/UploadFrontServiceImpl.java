package com.xc.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xc.api.service.UploadFrontService;
import com.xc.constant.Constant;
import com.xc.util.GenerateUUID;
import com.xc.util.JsonUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
@RestController
@RequestMapping(value = "/api/v1/upload", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE_All)
public class UploadFrontServiceImpl implements UploadFrontService {

	private static final String FILE = "file";

	private static final String FILE_DIR = System.getProperty("user.dir") + "/../upload";

	@Override
	@PostMapping("/file")
	public Object uploadFile(HttpServletRequest request) {
		FileInfo fileInfo = saveFile(request);
		JSONObject ret = new JSONObject();
		ret.put("code", 0);
		ret.put("msg", "上次成功");
		JSONObject data = new JSONObject();
		data.put("src", fileInfo.getSrc());
		data.put("title", fileInfo.getName());
		data.put("id", fileInfo.getId());
		ret.put("data", data);
		return JsonUtil.includePropToJson(ret);
	}

	private FileInfo saveFile(HttpServletRequest req) {
		FileInfo fileInfo = new FileInfo();
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
		File desFile = null;
		InputStream in = null;
		OutputStream out = null;
		String fileKey = "";
		try {
			MultipartFile file = request.getFile(FILE);
			String fileName = file.getOriginalFilename();
			fileInfo.setName(fileName);
			in = file.getInputStream();

			File fileDir = new File(FILE_DIR);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileKey = GenerateUUID.getUUID32();
			fileInfo.setId(fileKey);
			desFile = new File(FILE_DIR + "/" + fileKey);

			out = new BufferedOutputStream(new FileOutputStream(desFile));
			int size = 1024;
			byte[] data = new byte[size];
			int len = 0;
			while ((len = in.read(data)) != -1) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
		fileInfo.setSrc(req.getContextPath() + "/api/v1/upload/file/get/" + fileKey);
		return fileInfo;
	}

	@Override
	@GetMapping(value = "/file/get/{key}", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE_All)
	public Object getImg(@PathVariable String key, HttpServletResponse res) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		String sourceFile = FILE_DIR + "/" + key;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(sourceFile)));
			out = res.getOutputStream();
			int size = 1024;
			byte[] buffer = new byte[size];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	class FileInfo {
		private String id;
		private String src;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
