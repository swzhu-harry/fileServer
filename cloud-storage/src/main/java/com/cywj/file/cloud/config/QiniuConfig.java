package com.cywj.file.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
 
/**
 * 七牛配置信息
 * @author zhushiwu
 */
@Data
@ConfigurationProperties(prefix="qiniu")
@Configuration
public class QiniuConfig {
	private String httpBase;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String zoom;
}
