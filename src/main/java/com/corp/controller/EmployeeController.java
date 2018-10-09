package com.corp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.corp.config.AppConfigurator;
import com.corp.model.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	AppConfigurator appConfig;
	
	@PostConstruct
	public void createFile(){
		System.out.println(" Checking File Existence...");
		File directory = new File(appConfig.getFolderPath());
		File f = new File(appConfig.getFolderPath()+appConfig.getFileName());
		try{
			if(!directory.isDirectory()){
				System.out.println(" Directory Not Exists, creating");
				directory.mkdirs();
			}
			if(!f.exists()){
				System.out.println(" File Not Exists, creating");
				f.createNewFile();
			}
				
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Employee> getAllEmployees(){
		
		
		
		System.out.println(" File Name :: "+appConfig.getFileName());
		return null;
	}
	

}
