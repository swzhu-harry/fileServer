package com.cywj.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author zhushiwu
 * 文件上传配置
 */
@Data
@ConfigurationProperties(prefix="file.upload")
@Configuration
public class FileConfig {
	private String filedir="D:\\temp";
    private String whiteSuffix;
    //服务名称：七牛-qiniu，阿里云-aliyun，腾讯云-qyun,又拍云-upyun
    private String server = "qiniu";
}
