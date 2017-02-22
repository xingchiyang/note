package com.xc.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public class DirInfo {
	private String id;
	private String name;
	private String parentId; // 父类id
	private Integer type; // 目录类型
	private String createUserId;
	private Date createTime;
	private String modifyUserId;
	private Date modifyTime;
}
