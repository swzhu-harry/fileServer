package com.cywj.file.cloud.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.util.EnumUtil.QinuyErrorEnum;

/**
 * @author zhushiwu
 * 工具类
 */
public class GeneralUtil {

    public static void touch(File file) throws IOException {
		 //判断目标文件所在的目录是否存在  
      if(!file.getParentFile().exists()) { 
      	file.getParentFile().mkdirs();
      }  
      //判断文件是否存在，不存在则创建
		if (!file.exists()) {
			file.createNewFile();
		}
		boolean success = file.setLastModified(System.currentTimeMillis());
		if (!success)
			throw new IOException("Unable to set the last modification time for " + file);
	}

	public static boolean checkWhiteSuffix(String suffix,String whiteSuffix){
		if(!StringUtils.isEmpty(whiteSuffix)){
			String[] params = whiteSuffix.split(",");
			Set<String> suffixSets = new HashSet<String>();
			for (String str : params) {
				suffixSets.add(str.toLowerCase());
			}
			return suffixSets.contains(suffix.toLowerCase());
		}
		return true;
	}

	public static  String getKey(String file) {
		String extention = StringUtils.substringAfterLast(file, ".");
    	String uuid = UUID.randomUUID().toString().replace("-", "");
    	StringBuffer sb = new StringBuffer(uuid);
    	if(StringUtils.isEmpty(extention)){
    		return sb.toString();
    	}
        return sb.append(".").append(extention).toString();
	}
	
	public static File saveToLocal(MultipartFile file, String fileDir) throws Exception {
		String myFileName = file.getOriginalFilename();
		if(!StringUtils.isEmpty(myFileName)){
			myFileName = simpFileName(myFileName);
			// 构造文件path
			String path = new StringBuffer(fileDir)
					.append(File.separator).append(myFileName).toString();
			File localFile = new File(path);
			touch(localFile);
			file.transferTo(localFile);
			
			return localFile;
		}
		throw new RuntimeException("上传的文件名为空");
			
	}
	
	public static BaseAnswer error(QinuyErrorEnum error, String myFileName) {
		BaseAnswer ans = new BaseAnswer();
		ans.setErrorCode(error.getStatusCode());
		ans.setStatus(-100);
		ans.setErrorDecs(error.getDecs());
		ans.setFileName(simpFileName(myFileName));
		return ans;
	}
	
	public static String simpFileName(String myFileName) {
		if(myFileName.contains("/")|| myFileName.contains("\\")){
			if(myFileName.contains("/") ){
				myFileName = StringUtils.substringAfterLast(myFileName,"/");
			}
			if(myFileName.contains("\\") ){
				myFileName = StringUtils.substringAfterLast(myFileName,"\\");
			}
		}
		return myFileName;
	}
}
