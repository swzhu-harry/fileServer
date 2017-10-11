package com.cywj.file.cloud.service;

import java.io.File;
import java.io.InputStream;

/**
 * 接口：后续可扩展
 * 
 * @author zhushiwu
 *
 */
public interface CloudStorageService {
	/**
	 * 简单上传文件处理
	 * 
	 * @param localFilePath
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadSimple(String localFilePath) throws Exception;
	/**
	 * 简单上传文件处理 
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadFile(File file) throws Exception;
	
	/**
	 * 字节方式：这里转换为流方式上传
	 * 
	 * @param uploadBytes
	 * @param fname 原始文件名称
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadByte(byte[] uploadBytes,String fname) throws Exception;
	
	/**
	 * 流方式 上传
	 * 
	 * @param stream
	 * @param fname 原始文件名称
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadStream(InputStream stream,String fname) throws Exception;
}
