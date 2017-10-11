package com.cywj.file.cloud.service.impl;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;

/**
 * @author zhushiwu
 * 又拍云
 * 文档: https://github.com/upyun/java-sdk
 */
@Service
public class UpyunCloudStorageService implements CloudStorageService {

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
