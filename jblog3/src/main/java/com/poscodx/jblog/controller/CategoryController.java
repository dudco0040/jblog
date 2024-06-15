package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.CategoryVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	
	// 카테고리 목록 관리 
	@GetMapping("/blog/category")
	public String category(@PathVariable("id") String id, Model model) {
		// 목록 불러오기
		List<CategoryVo> categorySummary = categoryService.getCategory(id);
		// System.out.println("## Category List: " + categorySummary);
		
		//
		model.addAttribute("categorySummary", categorySummary);
		model.addAttribute("id",id);
		
		return "blog/category";
	}
	
	
	// 카테고리 추가 
	@PostMapping("/blog/category/insert")
	public String insert(
			@PathVariable("id") String id, 
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String description) {
		
		CategoryVo vo = new CategoryVo();
		vo.setId(id);
		vo.setName(name);
		vo.setDescription(description);
		
		categoryService.join(vo);
		System.out.println("## category insert success! " + vo);
		
		return "redirect:/" + id + "/blog/category";
	}
	
	// 카테고리 삭제
	@PostMapping("/blog/category/delete")
	public String delete(
			@PathVariable("id") String id,
			@RequestParam("categoryNo") Long categoryNo) {
		
		// category에 post가 없는 경우만 삭제 가능
		int count = postService.count(categoryNo);  
		if(count == 0) {
			categoryService.delete(categoryNo);
		}
		
		System.out.println("## delete success! category:" + categoryNo + "...");
		
		return "redirect:/" + id + "/blog/category";
	}
}
