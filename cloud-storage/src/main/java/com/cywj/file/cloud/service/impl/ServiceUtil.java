package com.cywj.file.cloud.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cywj.file.cloud.config.QiniuConfig;
import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.cloud.service.ReturnBody;
import com.cywj.file.cloud.util.EnumUtil.QinuyErrorEnum;
import com.cywj.file.cloud.util.EnumUtil.ZoomEnum;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import lombok.Getter;

/**
 * 服务工具类
 * @author zhushiwu
 */
@Component
public class ServiceUtil {
	final Long EXPIRE_SECONDS = 3600L;
	@Autowired
    private QiniuConfig conf;
	@Getter
	private UploadManager uploadManager;
	private Auth auth = null;
	
	public @PostConstruct void init(){
		//鉴权
		this.auth = Auth.create(conf.getAccessKey(), conf.getSecretKey());
		
		//配置上传管理器
		Configuration cfg = new Configuration(getZone());
		this.uploadManager = new UploadManager(cfg);
	}
	public String getUpToken() {
		//token
		return auth.uploadToken(conf.getBucket(), null, EXPIRE_SECONDS, getReturnBodyPolicy());
	}
	
	public BaseAnswer doResult(Response res) throws Exception {
		BaseAnswer ans = new BaseAnswer();
        if (!res.isOK()) {
        	ans.setStatus(-100);
        	QinuyErrorEnum eunm = QinuyErrorEnum.getQinuyErrorEnum(res.statusCode);
			if(eunm==QinuyErrorEnum.UNKNOW){
        		ans.setErrorCode(res.statusCode);
        		ans.setErrorDecs(res.bodyString());
        	}else{
        		ans.setErrorCode(eunm.getStatusCode());
        		ans.setErrorDecs(eunm.getDecs());
        	}
        }else{
        	 //解析上传成功的结果
        	ReturnBody re = new Gson().fromJson(res.bodyString(),ReturnBody.class);
    		String url = getDownloadPath(re.getKey());
        	ans.setStatus(200);
        	ans.setFileName(re.getFname());
        	ans.setUrl(url);
        }
		return ans;
	}

	private Zone getZone(){
    	return ZoomEnum.getZoneByZoomCode(conf.getZoom()).getZone();
    }
	
	private StringMap getReturnBodyPolicy() {
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"fname\":\"$(fname)\"}");
		return putPolicy;
	}
	
	private String getDownloadPath(String fName) {
		String httpBase = conf.getHttpBase();
		return new StringBuffer(httpBase)
				.append(httpBase.endsWith("/")?"":File.separator).append(fName).toString();
	}
}
