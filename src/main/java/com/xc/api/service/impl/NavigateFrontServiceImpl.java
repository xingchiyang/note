package com.xc.api.service.impl;

import com.xc.api.service.NavigateFrontService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
@RestController
@RequestMapping("/api/v1/navi")
public class NavigateFrontServiceImpl implements NavigateFrontService {
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String getDirs(){
		return "navi list";
	}
}
