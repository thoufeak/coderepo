package com.corp.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfigurator {
	
	

	@Value("${app.filedir}")
	private String folderPath;
	
	@Value("${app.filename}")
	private String fileName;

	public String getFolderPath() {
		return folderPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
		
}
