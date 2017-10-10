package com.cywj.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
 
@Data
@ConfigurationProperties(prefix="file.upload")
@Configuration
public class FileConfig {
	private String filedir="D:\\temp";
    private String whiteSuffix;
}
