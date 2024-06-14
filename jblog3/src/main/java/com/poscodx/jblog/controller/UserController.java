package com.poscodx.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categortyService;
	
	
	// 회원가입(join)
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {
		System.out.println("## vo : " + vo);
		userService.join(vo);  // 유저 추가 
		
		String id= vo.getId();
		// user에 추가가 완료되었는지 확인 
		System.out.println("## user insert");
		if(id!=null) {
			blogService.join(id); // 블로그 생성
			System.out.println("## blog insert");
			categortyService.join(vo.getId(), vo.getName()); // 카테고리 생성
			System.out.println("## category insert");
		}
		
		return "user/joinsuccess";
		
	}
	
	// 로그인(login)
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	

}
