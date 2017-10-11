package com.cywj.file.cloud.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
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
		if(!StringUtils.isBlank(whiteSuffix)){
			String[] params = whiteSuffix.split(",");
			Set<String> suffixSets = new HashSet<String>();
			for (String str : params) {
				suffixSets.add(str.toLowerCase());
			}
			return suffixSets.contains(suffix.toLowerCase());
		}
		return true;
	}

	public static  String getKey(File file) {
		try {
			return getKey(FileUtils.readFileToByteArray(file),file.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getName();
	}
	/**
	 * 文件字节 计算hash，文件内容没变化 hash一样
	 * @param b
	 * @param fname
	 * @return
	 */
	public static  String getKey(byte[] b,String fname) {
		try {
			StringBuffer sb = new StringBuffer(byteToHexString(b));
			String extention = StringUtils.substringAfterLast(fname, ".");
			if(StringUtils.isBlank(extention)){
				return sb.toString();
			}
			return sb.append(".").append(extention).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fname;
	}
	
	public static File saveToLocal(MultipartFile file, String fileDir) throws Exception {
		String myFileName = file.getOriginalFilename();
		if(!StringUtils.isBlank(myFileName)){
			myFileName = simpFileName(myFileName);
			// 构造文件path
			String path = new StringBuffer(fileDir)
					.append(File.separator).append(myFileName).toString();
			File localFile = new File(path);
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(localFile));
			out.write(file.getBytes());
			out.flush();
			out.close();
			
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
	
	private static String byteToHexString(byte[] bytes) throws Exception {
		MessageDigest complete = MessageDigest.getInstance("MD5");
		complete.update(bytes, 0, bytes.length); 
		
		byte[] di = complete.digest();
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
	
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < di.length; i++) {  
			ob[0] = Digit[( di[i] >>> 4) & 0X0f];
			ob[1] = Digit[ di[i] & 0X0F];
			sb.append(ob);
	    }  
		return sb.toString();
	} 
}
