package com.xc.api.service.impl;

import com.xc.api.service.DirectoryFrontService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping("/api/v1/navi")
public class DirectoryFrontServiceImpl implements DirectoryFrontService {
	@GetMapping(value = "/query")
	public String getDirs(){
		return "navi list";
	}
}
