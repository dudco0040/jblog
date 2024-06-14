package com.poscodx.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.PostService;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class PostController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/blog/write")
	public String write() {
		return "/blog/write";
	}
	
}
