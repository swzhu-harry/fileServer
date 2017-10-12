package com.cywj.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;
import com.cywj.file.cloud.util.EnumUtil.CloudServiceEnum;
import com.cywj.file.cloud.util.EnumUtil.QinuyErrorEnum;
import com.cywj.file.cloud.util.GeneralUtil;
import com.cywj.file.common.SpringUtils;
import com.cywj.file.config.FileConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhushiwu
 * 文件上传服务
 */
@Slf4j
@Service
public class FileUploadService {
	private static Gson gson = new GsonBuilder().create();

	@Autowired
	private FileConfig config;
	//存储服务
	private CloudStorageService cloud;
	
	/**
	 * 初始化
	 */
	public @PostConstruct void init(){
		log.debug("上传服务 初始化-----------start-------------");
		//根据配置 选择存储服务
		Class<?> serviceClass = CloudServiceEnum.getCloudServiceEnum(config.getServer()).getServiceClass();
		this.cloud = (CloudStorageService) SpringUtils.getBean(serviceClass);
		log.debug("上传服务 初始化------------end------------");
	}
	/**
	 * 批量上传失败情况，此处没有进行云端数据回滚
	 * 
	 * @param request
	 * @return
	 */
	public List<BaseAnswer> uploadBatch(HttpServletRequest request) {
		List<BaseAnswer> ret = new ArrayList<>();
		String myFileName = null;
		//保持到本地
		File localFile =  null;
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					log.debug("上传的文件大小size:"+(file == null ? 0 : file.getSize()));
					if (file != null && file.getSize() > 0) {
						myFileName = file.getOriginalFilename();
						if(!StringUtils.isBlank(myFileName)){
							//文件类型白名单 验证
							String extention = null;
							if(myFileName.lastIndexOf(".") != -1){
								extention = StringUtils.substringAfterLast(myFileName, ".");
								if(!GeneralUtil.checkWhiteSuffix(extention,config.getWhiteSuffix())){
									ret.add(GeneralUtil.error(QinuyErrorEnum.ERROR_4001,myFileName));
									return ret;
								}
							}
							localFile = GeneralUtil.saveToLocal(file,config.getFiledir());
							log.debug("上传到七牛云服务器 start");
							
							//文件方式上传
							BaseAnswer res = cloud.uploadFile(localFile);
							
							//字节数组方式上传
							//BaseAnswer res = cloud.uploadByte(file.getBytes(),GeneralUtil.simpFileName(myFileName));
							
							ret.add(res);
							//释放 关闭资源
							delete(localFile);
							log.debug("上传到七牛云服务器 end res：{}" ,gson.toJson(res));
						}
					}
				}
			}
		} catch (Exception e) {
			//上传到七牛云服务器 失败
			log.error("上传到七牛云服务器 失败",e);
			ret.add(GeneralUtil.error(QinuyErrorEnum.UNKNOW,myFileName));
			return ret;
		} finally{
			//释放 关闭资源
			delete(localFile);
		}
		return ret;
	}
	/**
	 * 释放资源
	 * @param localFile
	 * @param in
	 */
	private void delete(File localFile) {
		if(localFile!=null){
			localFile.delete();
			localFile = null;
		}
	}
}
