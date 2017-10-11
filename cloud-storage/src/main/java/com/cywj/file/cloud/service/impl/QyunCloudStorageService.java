package com.cywj.file.cloud.service.impl;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;

/**
 * @author zhushiwu
 * 腾讯云对象存储服务COS
 * 文档: https://www.qcloud.com/doc/product/430/5944
 */
@Service
public class QyunCloudStorageService implements CloudStorageService {

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
		throw new IllegalArgumentException("不支持的方法, 请使用upload(File file, String format)");
	}

	@Override
	public BaseAnswer uploadStream(InputStream stream, String fname) throws Exception {
		throw new IllegalArgumentException("不支持的方法, 请使用upload(File file, String format)");
	}

}
