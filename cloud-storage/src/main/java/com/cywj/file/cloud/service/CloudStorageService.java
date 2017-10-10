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
	 * 简单上传文件处理:key uuid 加文件后缀方式
	 * 
	 * @param localFilePath
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadSimple(String localFilePath) throws Exception;
	/**
	 * 简单上传文件处理:key uuid 加文件后缀方式
	 * 
	 * @param localFilePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadSimple(String localFilePath,String key) throws Exception;
	/**
	 * 简单上传文件处理:key uuid 加文件后缀方式
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadFile(File file) throws Exception;
	/**
	 * 简单上传文件处理
	 * 
	 * @param file
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadFile(File file,String key) throws Exception;
	
	/**
	 * 字节方式：这里转换为流方式上传，以为字节方式直接上传，不能进行自定义设置
	 * 
	 * @param uploadBytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadByte(byte[] uploadBytes,String key) throws Exception;
	
	/**
	 * 字节方式：这里转换为流方式上传，以为字节方式直接上传，不能进行自定义设置
	 * 
	 * @param uploadBytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadByte(byte[] uploadBytes,String key,String fname) throws Exception;
	
	/**
	 * 流方式 支持自定义参数上传
	 * 
	 * @param stream
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadStream(InputStream stream,String key) throws Exception;
	/**
	 * 流方式 支持自定义参数上传
	 * 
	 * @param stream
	 * @param key
	 * @param fname 进行自定义（设置原始文件名称）
	 * @return
	 * @throws Exception
	 */
	public BaseAnswer uploadStream(InputStream stream,String key,String fname) throws Exception;
}
