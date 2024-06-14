package com.poscodx.jblog.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;

//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//	@Autowired
//	private ServletContext servletContext;
//	
//	@Autowired
//	private BlogService blogService;
//
//	@Autowired
//	private FileUploadService fileuploadService;
//	
//	@RequestMapping("/basic")
//	public String main(Model model) {
//		BlogVo vo = blogService.getBlog();
//		System.out.println("## getBlog: " + vo);
//		
//		model.addAttribute("blogVo", vo);
//		
//		return "/blog/admin-basic";
//	}
//	
//}
