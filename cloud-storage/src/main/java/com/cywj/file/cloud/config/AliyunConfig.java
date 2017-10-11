package com.cywj.file.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="aliyun")
@Configuration
public class AliyunConfig {
	private String httpBase;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
}
