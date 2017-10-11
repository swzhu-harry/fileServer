package com.cywj.file.cloud.service.impl;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;

/**
 * 阿里云
 * @author zhushiwu
 * 文档: https://help.aliyun.com/document_detail/32008.html?spm=5176.doc32008.3.3.3YkvaP
 */
@Service
public class AliyunCloudStorageService implements CloudStorageService {

	@Override
	public BaseAnswer uploadSimple(String localFilePath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseAnswer uploadFile(File file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseAnswer uploadByte(byte[] uploadBytes, String fname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseAnswer uploadStream(InputStream stream, String fname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
