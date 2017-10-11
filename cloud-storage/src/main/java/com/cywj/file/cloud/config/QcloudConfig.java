package com.cywj.file.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="qyun")
@Configuration
public class QcloudConfig {
	private String httpBase;
    private int appId;
    private String secretId;
    private String secretKey;
    private String bucket;
}
