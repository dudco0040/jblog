package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categortService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	// 블로그(BLOG) 메인페이지 
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo:[\\d]+}/{postNo:[\\d]+}" })
	public String index(
				@PathVariable("id") String id,
				@PathVariable(value="categoryNo", required=false) Optional<Long> categoryNo,
				@PathVariable(value="postNo", required=false) Optional<Long> postNo,
				Model model) {

		// 블로그 정보 반영
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("blogVo", vo);
		System.out.println("=====" + vo);
		
		// main으로 들어온 경우, category 매핑 - 비어있을 경우, default value(catgoryNo, postNo)을 지정
		// 가장 최근 category, post 번호 불러오기 
		
		
		// 1. id를 받아서 category 목록 출력
		List<CategoryVo> categories = categortService.getCategory(id);
		model.addAttribute("categories", categories);
		System.out.println("## categories: " + categories);  // name 사용
		System.out.println("## ?category= " + categoryNo);
		

		// 2. id, category 받아서 post 목록 출력
		Long categoryNoValue;
		if(categoryNo.isPresent()) {
			categoryNoValue = categoryNo.orElse(null);
			System.out.println("## ?category= " + categoryNoValue);
		} else {
			// category가 없는 경우 최근 카테고리를 가져옴 
			categoryNoValue = categortService.currentCategory(id); 
			System.out.println("## Default Value (current categoryNo): " + categoryNoValue);
		}
		
		int postCount = postService.count(categoryNoValue);
		System.out.println("## postCount:" + postCount);
			
		if(postCount>0) {  // post 가 없을 수 있음 - 개수를 먼저 세고, 있으면 리스트 출력? 0, 1, 여러개..? , jsp 파일은 어떻게..?
			List<PostVo> posts = postService.getPostList(categoryNoValue);
			model.addAttribute("posts", posts);
			
			System.out.println("## posts: " + posts);  // title, regDate
		}
		
		// 3. post 번호 받아서 값 출력 
		if(postNo.isPresent()) {
			Long postNoValue = postNo.orElse(null);
			PostVo postVo = postService.getPost(categoryNoValue, postNoValue);
			model.addAttribute("postVo", postVo);
			System.out.println("## post: " + postVo.getTitle() + ", " + postVo.getContents());

		} else {
			if (postCount > 0) {
                PostVo postVo = postService.getRecentPost(categoryNoValue);
                model.addAttribute("postVo", postVo);
    			System.out.println("## post: " + postVo.getTitle() + ", " + postVo.getContents());

		}
//		else {
//			Long currentCategoryNo = categortService.currentCategory(id);
//			System.out.println("## Default Value (current categoryNo): " + currentCategoryNo);
//			
//			Long currentPostNo = postService.currentPost(currentCategoryNo);
//			System.out.println("## Default Value (current currentPostNo): " + currentPostNo);
//			
//			if(currentPostNo==null) {
//				//return "redirect:/" + id + "/" + currentCategoryNo;
//			} else {
//				//return "redirect:/" + id + "/" + currentCategoryNo + "/" + currentPostNo;
//			}
//			
//		}
		
		}
		return "blog/main";
	}
	
	
	// 관리(ADMIN) 페이지 
	// 블로그 정보 가져오기 
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model) {
		System.out.println("## getBlog(id): " + id);
		BlogVo vo = blogService.getBlog(id);
		System.out.println("## getBlog: " + vo);
		
		model.addAttribute("blogVo", vo);
		
		return "blog/admin-basic";
	}
	
	// 블로그 정보 변경 
	@Auth
	@RequestMapping("/admin/update")
	public String adminUpdate(BlogVo vo, @RequestParam(value="logo-file") MultipartFile file) {
		
		String logo = fileuploadService.restore(file);
		if(logo != null) {
			vo.setLogo(logo);
		}
		

		System.out.println("## updateFile(logo): " + logo);
		System.out.println("## updateBlog Info: " + vo);
		
		blogService.updateBlog(vo);
		
		
		return "redirect:/" + vo.getId() +"/admin/basic";
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id) {
		return "blog/admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		return "blog/admin-write";
	}

}
