package com.cywj.file.cloud.service.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;
import com.cywj.file.cloud.util.GeneralUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**
 * @author zhushiwu
 * 七牛云存储
 * 文档: https://developer.qiniu.com/kodo/sdk/1239/java
 */

@Service
public class QiniuCloudStorageService implements CloudStorageService {

	@Autowired
    private ServiceUtil util;
	
	@Override
	public BaseAnswer uploadSimple(String localFilePath) throws Exception{
		return this.uploadFile(new File(localFilePath));
	}
	
	@Override
	public BaseAnswer uploadFile(File file) throws Exception{
		try {
		    Response response = util.getUploadManager().put(file, GeneralUtil.getKey(file), util.getUpToken());
		    return util.doResult(response,null);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response,null);
		}
	}
	
	@Override
	public BaseAnswer uploadByte(byte[] bytes,String fname) throws Exception{
		try {
			//设置原始文件，在返回结果中带回
			Response response = util.getUploadManager().put(bytes, GeneralUtil.getKey(bytes,fname), util.getUpToken());
		    return util.doResult(response,fname);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response,fname);
		}
	}

	@Override
	public BaseAnswer uploadStream(InputStream in,String fname) throws Exception{
		try {
			//设置原始文件，在返回结果中带回
		    String key = GeneralUtil.getKey(IOUtils.toByteArray(in),fname);
			Response response = util.getUploadManager().put(in, key, util.getUpToken(),null,null);
		    return util.doResult(response,fname);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response,fname);
		}
	}
}
