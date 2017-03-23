package com.xc.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xc.api.service.UploadFrontService;
import com.xc.constant.Constant;
import com.xc.util.GenerateUUID;
import com.xc.util.JsonUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
@RestController
@RequestMapping(value = "/api/v1/upload", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE_All)
public class UploadFrontServiceImpl implements UploadFrontService {

	private static final String FILE_NAME = "upload_file";

	@Override
	@PostMapping("/img")
	public Object uploadImg(HttpServletRequest request) {
		String fileName = saveFile(request);
		JSONObject ret = new JSONObject();
		ret.put("success", true);
		ret.put("msg", "上次成功");
		ret.put("file_path", fileName);
		return JsonUtil.includePropToJson(ret);
	}

	private String saveFile(HttpServletRequest req) {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
		File desFile = null;
		InputStream in = null;
		OutputStream out = null;
		String fileDataFileName = "";
		try {
			MultipartFile file = request.getFile(FILE_NAME);
			in = file.getInputStream();

			String saveRealFilePath = System.getProperty("user.dir") + "/upload";
			System.out.println(saveRealFilePath);
			File fileDir = new File(saveRealFilePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDataFileName = GenerateUUID.getUUID32();
			desFile = new File(saveRealFilePath + "/" + fileDataFileName);

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
		return fileDataFileName;
	}

	@Override
	@GetMapping("/img/get/{id}")
	public Object getImg(@PathVariable String id) {
		return null;
	}

}
