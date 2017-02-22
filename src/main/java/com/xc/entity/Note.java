package com.xc.entity;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public class Note {
	private String id;
	private String dirId;
	private String title; // 标题
	private String content; // 内容
	private Integer type; // 笔记类型，markdown、常规笔记
	private String path; // 笔记所在系统文件中的目录
}
