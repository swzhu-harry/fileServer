package com.cywj.file.cloud.service;

import lombok.Data;

@Data
public class BaseAnswer {
	Integer status;
	Integer	errorCode;
	String 	errorDecs;
	String 	fileName;
	String	url;
	
}
