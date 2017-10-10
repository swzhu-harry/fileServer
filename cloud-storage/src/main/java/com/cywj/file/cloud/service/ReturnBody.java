package com.cywj.file.cloud.service;

import lombok.Data;

/**
 * 自定义返回对象
 * @author zhushiwu
 */
@Data
public class ReturnBody {
	private String key;
	private String hash;
	private String fname;
	private String bucket;
	private Long fsize;
}
