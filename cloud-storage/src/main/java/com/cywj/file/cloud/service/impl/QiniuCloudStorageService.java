package com.cywj.file.cloud.service.impl;

import java.io.ByteArrayInputStream;
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
import com.qiniu.util.StringMap;

/**
 * @author zhushiwu
 * 七牛云存储
 * 文档: http://developer.qiniu.com/code/v7/sdk/java.html
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
		    return util.doResult(response);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response);
		}
	}
	
	@Override
	public BaseAnswer uploadByte(byte[] uploadBytes,String fname) throws Exception{
		ByteArrayInputStream in = new ByteArrayInputStream(uploadBytes);
	    BaseAnswer uploadStream = uploadStream(in,fname);
	    in.close();
		return uploadStream;
	}

	@Override
	public BaseAnswer uploadStream(InputStream in,String fname) throws Exception{
		try {
			//设置原始文件，在返回结果中带回
			StringMap param = new StringMap();
			param.put("fname", fname);
		    String key = GeneralUtil.getKey(IOUtils.toByteArray(in),fname);
			Response response = util.getUploadManager().put(in, key, util.getUpToken(),param,null);
		    return util.doResult(response);
		} catch (QiniuException ex) {
		    return util.doResult(ex.response);
		}
	}
}
