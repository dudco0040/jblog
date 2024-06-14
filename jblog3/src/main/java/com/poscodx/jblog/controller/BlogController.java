package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	
	@RequestMapping({"", "/{categoryNo:[\\\\\\\\d]+}", "/{categoryNo:[\\\\d]+}/{postNo:[\\\\d]+}" })
	public String index(
		@PathVariable("id") String id,
		@PathVariable(value="categoryNo", required=false) Optional<Long> categoryNo,
		@PathVariable(value="postNo", required=false) Optional<Long> postNo) {

		return "blog/main";
	}
	
	// 블로그 정보 가져오기 
	// @Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model) {
		System.out.println("## getBlog(id): " + id);
		BlogVo vo = blogService.getBlog(id);
		System.out.println("## getBlog: " + vo);
		
		model.addAttribute("blogVo", vo);
		
		return "blog/admin-basic";  // 사진 안보임 
	}
	
	// 블로그 정보 변경 
	// @Auth
	@RequestMapping("/admin/update")
	public String adminUpdate(BlogVo vo, @RequestParam(value="logo-file") MultipartFile file) {
		
		String logo = fileuploadService.restore(file);
		if(logo != null) {
			vo.setLogo(logo);
		}
		

		System.out.println("## updateFile(logo): " + logo);
		System.out.println("## updateBlog Info: " + vo);
		
		blogService.updateBlog(vo);
		
		
		return "redirect:/" + vo.getId() +"/admin/basic";  // 사진 안보임 
	}

	// @Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id) {
		return "blog/admin-category";
	}
	
	//@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		return "blog/admin-write";
	}

}
