package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}
	
	// 현재 정보 불러오기 
	public BlogVo getBlog(String id) {
		return blogRepository.find(id);
	}
	
	// 수정하기 
	@Transactional
	public void updateBlog(BlogVo vo) {
		blogRepository.update(vo);
	}

	// 블로그 생성
	@Transactional
	public void join(String id) {
		blogRepository.insert(id);
	}

}
