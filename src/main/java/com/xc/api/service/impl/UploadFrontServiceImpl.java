package com.xc.api.service.impl;

import com.xc.api.service.UploadFrontService;
import com.xc.constant.Constant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/03/07 0007.
 */
@RestController
@RequestMapping(value = "/api/v1/upload", produces = Constant.MEDIA_TYPE, consumes = Constant.MEDIA_TYPE)
public class UploadFrontServiceImpl implements UploadFrontService{

	@Override
	@PostMapping("/img")
	public Object uploadImg(HttpServletRequest request) {

		return null;
	}
}
