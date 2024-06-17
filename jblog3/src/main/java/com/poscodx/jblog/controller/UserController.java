package com.poscodx.jblog.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.CategoryVo;
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
	public String join(@Valid UserVo vo, BindingResult result, Model model) {
		// Bean Validation
		if(result.hasErrors()) {
			// System.out.println(result);
			Map<String, Object> map = result.getModel();
			model.addAttribute(map);
			
			return "user/join";
		}
		
		System.out.println("## vo : " + vo);
		userService.join(vo);  // 유저 추가 
		System.out.println("## user insert");
		
		String id= vo.getId();
		if(id!=null) {
			// 블로그 생성
			blogService.join(id); 
			System.out.println("## blog insert");
			
			// 카테고리 생성 (category default setting)
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setId(vo.getId());
			categoryVo.setName("first category");
			categoryVo.setDescription("welcome! " + vo.getName());
			categortyService.join(categoryVo); 
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
