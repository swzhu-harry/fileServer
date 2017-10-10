package com.cywj.file.cloud.service;

import lombok.Data;

/**
 * 返回信息
 * @author zhushiwu
 */
@Data
public class BaseAnswer {
	Integer status;
	Integer	errorCode;
	String 	errorDecs;
	String 	fileName;
	String	url;
	
}
