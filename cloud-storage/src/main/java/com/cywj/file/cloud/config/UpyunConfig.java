package com.cywj.file.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="upyun")
@Configuration
public class UpyunConfig {
	private String httpBase;
    private String bucket;
    private String username;
    private String password;

}
