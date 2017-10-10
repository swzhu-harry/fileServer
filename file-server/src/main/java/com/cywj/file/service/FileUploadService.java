package com.cywj.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.CloudStorageService;
import com.cywj.file.cloud.util.EnumUtil.QinuyErrorEnum;
import com.cywj.file.cloud.util.GeneralUtil;
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
	
	@Autowired
	private CloudStorageService cloud;

	/**
	 * 批量上传失败情况，此处没有进行云端数据回滚
	 * 
	 * @param request
	 * @return
	 */
	public List<BaseAnswer> uploadBatch(HttpServletRequest request) {
		List<BaseAnswer> ret = new ArrayList<>();
		String myFileName = null;
		try {
			//白名单
			String whiteSuffix = config.getWhiteSuffix(); 
			//文件存放目录
			String reqFileDir = request.getParameter("fileDir");
			String fileDir = config.getFiledir(); 
			if(!StringUtils.isEmpty(reqFileDir) && !"null".equalsIgnoreCase(reqFileDir))
				fileDir = reqFileDir;
			
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					//保持到本地
					File localFile =  null;
					try{
						// 取得上传文件
						MultipartFile file = multiRequest.getFile(iter.next());
						log.debug("上传的文件大小size:"+(file == null ? 0 : file.getSize()));
						if (file != null && file.getSize() > 0) {
							myFileName = file.getOriginalFilename();
							if(!StringUtils.isEmpty(myFileName)){
								//文件类型白名单 验证
								String extention = null;
								if(myFileName.lastIndexOf(".") != -1){
									extention = StringUtils.substringAfterLast(myFileName, ".");
									if(!GeneralUtil.checkWhiteSuffix(extention,whiteSuffix)){
										ret.add(GeneralUtil.error(QinuyErrorEnum.ERROR_4001,myFileName));
										return ret;
									}
								}
								localFile = GeneralUtil.saveToLocal(file, fileDir);
								// 同步数据到七牛
								log.debug("上传到七牛云服务器 start");
								BaseAnswer res = cloud.uploadFile(localFile);
								ret.add(res);
								log.debug("上传到七牛云服务器 end res：{}" ,gson.toJson(res));
							}
						}
					}finally{
						//删除本地文件
						if(localFile!=null)
							localFile.delete();
					}
				}
			}
		} catch (Exception e) {
			//上传到七牛云服务器 失败
			log.error("上传到七牛云服务器 失败",e);
			ret.add(GeneralUtil.error(QinuyErrorEnum.UNKNOW,myFileName));
			return ret;
		}
		return ret;
	}
}