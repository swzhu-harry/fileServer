package com.cywj.file.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
 
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
