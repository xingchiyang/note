package com.xc.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xc.api.service.AttachFrontService;
import com.xc.constant.AttachConstant;
import com.xc.constant.Constant;
import com.xc.entity.Attach;
import com.xc.logic.AttachLogic;
import com.xc.util.GenerateUUID;
import com.xc.util.JsonUtil;
import com.xc.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
@RestController
@RequestMapping(value = "/api/v1/attach", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE_All)
public class AttachFrontServiceImpl implements AttachFrontService {

	private static final String FILE = "file";

	private static final String FILE_DIR = System.getProperty("user.dir") + "/../upload";

	@Autowired
	private AttachLogic attachLogic;

	@Override
	@PostMapping("/upload")
	public Object uploadFile(@RequestParam(value = "type", required = false) String type, HttpServletRequest request) {
		FileInfo fileInfo = saveFile(request);
		if (AttachConstant.TYPE_ATTACH.equals(type)) {
			// 保存附件
			Attach attach = new Attach();
			attach.setId(fileInfo.getId());
			attach.setName(fileInfo.getName());
			attach.setSize(fileInfo.getSize());
			attachLogic.createAttach(attach);
		}

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
		String fileName = "";
		try {
			MultipartFile file = request.getFile(FILE);
			fileName = file.getOriginalFilename();
			long fileSize = 0;
			fileSize = file.getSize() / 1024;
			fileInfo.setSize(fileSize);
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
		fileInfo.setSrc(req.getContextPath() + "/api/v1/attach/get?fileKey=" + fileKey + "&fileName=" + fileName);
		return fileInfo;
	}

	@Override
	@GetMapping("/get")
	public Object getFile(@RequestParam("fileKey") String fileKey, @RequestParam("fileName") String fileName,
			HttpServletResponse res) {
		if (StringUtils.isEmpty(fileKey)) {
			return JsonUtil.includePropToJson(null);
		}
		String sourceFile = FILE_DIR + "/" + fileKey;
		InputStream in = null;
		OutputStream out = null;
		try {
			res.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/octet-stream");
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
		return JsonUtil.includePropToJson(null);
	}

	@Override
	@GetMapping("/remove/{key}")
	public Object removeFile(@PathVariable String key) {
		if (StringUtils.isEmpty(key)) {
			return JsonUtil.includePropToJson(null);
		}
		File file = new File(FILE_DIR + "/" + key);
		if (file.exists()) {
			file.delete();
		}

		// 删除附件
		attachLogic.removeAttach(key);

		return JsonUtil.includePropToJson(null);
	}

	@GetMapping("/query")
	@Override
	public String getAttachList(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "sortType", required = false) Integer sortType) {
		Pagination<Attach> p = attachLogic.getAttachsList(name, page, size, sortKey, sortType);
		return JsonUtil.includePropToJson(p.formate());
	}

	class FileInfo {
		private String id;
		private String src;
		private String name;
		private long size;

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

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
