package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	// 관리 페이지로 이동
	@RequestMapping(value="/blog/write", method=RequestMethod.GET)
	public String write(@PathVariable("id") String id, Model model) {
		
		// 카테고리 목록 보이게 (split button)
		List<CategoryVo> categories = categoryService.getCategory(id);  // 사용자 본인 카테고리만 보이게 
		model.addAttribute("categories", categories);
		
		
		return "/blog/write";
	}
	
	// 글쓰기(insert post)
	@RequestMapping(value="/blog/write", method=RequestMethod.POST)
	public String insert(@PathVariable("id") String id,  // userId: Request Mapping
			@RequestParam("categoryNo") Long categoryNo,  //  category no(FK)
			@RequestParam(required = true) String title,  		 // write input 
			@RequestParam(required = true) String contents) {	 // write input 
		
		PostVo vo = new PostVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setCategoryNo(categoryNo);
		
		System.out.println("====write" + vo);  // 왜인지 출력은 안됨 
		
		postService.insert(vo);
		
		return "redirect:/"+ id +"/blog/write";
	}
	
}
