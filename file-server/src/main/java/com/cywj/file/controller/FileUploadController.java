package com.cywj.file.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cywj.file.cloud.service.BaseAnswer;
import com.cywj.file.service.FileUploadService;

import io.swagger.annotations.Api;

/**
 * @author swzhu
 */
@Controller
@Api(tags = { "File upload API v1.0" })
@RequestMapping(produces="application/json;charset=UTF-8") 
public class FileUploadController {
	
	@Autowired
	private FileUploadService service;

	// 访问路径为：http://127.0.0.1:8080/file
	@RequestMapping(value="/file", method = RequestMethod.GET)
	public String file() {
		return "/file";
	}

	// 访问路径为：http://127.0.0.1:8080/mutifile
    @RequestMapping(value="/mutifile", method = RequestMethod.GET)
    public String mutifile() {
      return "/mutifile";
    }
    
	/**
	 * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile
	 * 
	 * @param request
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
	public List<BaseAnswer> handleFileUpload(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("--------------------------");
		return service.uploadBatch(request);
	}
	
}
