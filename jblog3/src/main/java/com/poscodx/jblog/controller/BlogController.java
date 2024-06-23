package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
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
				Model model, HttpSession session) {

		// 블로그 정보 반영
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("blogVo", vo);
		System.out.println("=====" + vo);
		
		// main으로 들어온 경우, category 매핑 - 비어있을 경우, default value(catgoryNo, postNo)을 지정
		// 가장 최근 category, post 번호 불러오기 
		
		
		// 1. id를 받아서 category 목록 출력
		List<CategoryVo> categories = categoryService.getCategory(id);
		model.addAttribute("categories", categories);
		System.out.println("## categories: " + categories);  // name 사용
		System.out.println("## ?category= " + categoryNo);
		

		// 2. id, category 받아서 post 목록 출력
//		Long categoryNoValue;
//		if(categoryNo.isPresent()) {
//			categoryNoValue = categoryNo.orElse(null);
//			System.out.println("## ?category= " + categoryNoValue);
//		} else {
//			// category가 없는 경우 최근 카테고리를 가져옴 
//			categoryNoValue = categoryService.currentCategory(id); 
//			System.out.println("## Default Value (current categoryNo): " + categoryNoValue);
//		}
		Long categoryNoValue = categoryNo.orElseGet(() -> categoryService.currentCategory(id));

		
		int postCount = postService.count(categoryNoValue);
		System.out.println("## postCount:" + postCount);
			
		if(postCount>0) {  // post 가 없을 수 있음 - 개수를 먼저 세고, 있으면 리스트 출력? 0, 1, 여러개..? , jsp 파일은 어떻게..?
			List<PostVo> posts = postService.getPostList(categoryNoValue);
			model.addAttribute("posts", posts);
			
			System.out.println("## posts: " + posts);  // title, regDate
		}
		
		// 3. post 번호 받아서 값 출력 
//		if(postNo.isPresent()) {
//			Long postNoValue = postNo.orElse(null);
//			PostVo postVo = postService.getPost(categoryNoValue, postNoValue);
//			model.addAttribute("postVo", postVo);
//			System.out.println("## post: " + postVo.getTitle() + ", " + postVo.getContents());
//
//		} else {
//			if (postCount > 0) {
//                PostVo postVo = postService.getRecentPost(categoryNoValue);
//                model.addAttribute("postVo", postVo);
//    			System.out.println("## post: " + postVo.getTitle() + ", " + postVo.getContents());
//
//			}
//		
//		}
		
		PostVo postVo = postNo.map(postNoValue -> postService.getPost(categoryNoValue, postNoValue))
                .orElseGet(() -> postCount > 0 ? postService.getRecentPost(categoryNoValue) : null);
		
		if (postVo != null) {
		model.addAttribute("postVo", postVo);
		}

		
		
		// 로그인 & 본인인증
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		model.addAttribute("authUser", authUser);
		model.addAttribute("id", id);
		
		return "blog/main";
	}
	
	
	// 관리(ADMIN) 페이지 
	// 블로그 정보 가져오기 
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model, HttpSession session) {
		
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		System.out.println("## authUser: "+ authUser.getId());
		
		if(id.equals(authUser.getId())) {
			System.out.println("## getBlog(id): " + id);
			BlogVo vo = blogService.getBlog(id);
			System.out.println("## getBlog: " + vo);
			
			model.addAttribute("blogVo", vo);
			model.addAttribute("id", vo.getId());
			
			return "blog/admin-basic";
		} else {
			return "redirect:/main/index";
		}
		
		
	}
	
	// 블로그 정보 변경 
	@Auth
	@RequestMapping("/admin/update")
	public String adminUpdate(BlogVo vo, @RequestParam(value="logo-file") MultipartFile file, HttpSession session) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		System.out.println("## authUser: "+ authUser.getId());
		String id = vo.getId();
		if(id.equals(authUser.getId())) {
			String logo = fileuploadService.restore(file);
			if(logo != null) {
				vo.setLogo(logo);
			}
			
			System.out.println("## updateFile(logo): " + logo);
			System.out.println("## updateBlog Info: " + vo);
			
			blogService.updateBlog(vo);
			
			return "redirect:/" + vo.getId() +"/admin/basic";
		} else {
			return "redirect:/main/index";
		}
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model, HttpSession session) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		System.out.println("## authUser: "+ authUser.getId());
		if(id.equals(authUser.getId())) {
		
			BlogVo vo = blogService.getBlog(id);
			System.out.println("## getBlog: " + vo);
			model.addAttribute("blogVo", vo);
	
			return "blog/admin-category";
		} else {
			return "redirect:/main/index";
		}
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model, HttpSession session) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		System.out.println("## authUser: "+ authUser.getId());
		if(id.equals(authUser.getId())) {
		
			BlogVo vo = blogService.getBlog(id);
			model.addAttribute("blogVo", vo);
			
			return "blog/admin-write";
		} else {
			return "redirect:/main/index";
			}
	}

}
