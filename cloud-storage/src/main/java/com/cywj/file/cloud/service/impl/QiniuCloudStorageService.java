package com.cywj.file.cloud.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;
import com.cywj.file.cloud.util.GeneralUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.util.StringMap;

/**
 * @author zhushiwu
 * 七牛云存储
 * 文档: http://developer.qiniu.com/code/v7/sdk/java.html
 */

@Service("qiniu")
public class QiniuCloudStorageService implements CloudStorageService {

	@Autowired
    private ServiceUtil util;
	
	@Override
	public BaseAnswer uploadSimple(String localFilePath) throws Exception{
		return this.uploadSimple(localFilePath,GeneralUtil.getKey(localFilePath));
	}
	
	@Override
	public BaseAnswer uploadSimple(String localFilePath,String key) throws Exception{
		return this.uploadFile(new File(localFilePath),key);
	}
	
	@Override
	public BaseAnswer uploadFile(File file) throws Exception{
		return this.uploadFile(file,GeneralUtil.getKey(file.getName()));
	}
	
	@Override
	public BaseAnswer uploadFile(File file,String key) throws Exception{
		try {
		    Response response = util.getUploadManager().put(file, key, util.getUpToken());
		    return util.doResult(response);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response);
		}
	}
	
	@Override
	public BaseAnswer uploadByte(byte[] uploadBytes,String key) throws Exception{
		return uploadByte(uploadBytes,key,key);
	}
	
	@Override
	public BaseAnswer uploadByte(byte[] uploadBytes,String key,String fname) throws Exception{
		ByteArrayInputStream in = new ByteArrayInputStream(uploadBytes);
	    BaseAnswer uploadStream = uploadStream(in,key,fname);
	    in.close();
		return uploadStream;
	}
	
	@Override
	public BaseAnswer uploadStream(InputStream stream,String key) throws Exception{
		return uploadStream(stream,key,key);
	}

	@Override
	public BaseAnswer uploadStream(InputStream stream,String key,String fname) throws Exception{
		try {
			StringMap param = new StringMap();
			param.put("fname", fname);
		    Response response = util.getUploadManager().put(stream, key, util.getUpToken(),param,null);
		    return util.doResult(response);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response);
		}
	}
	
}
