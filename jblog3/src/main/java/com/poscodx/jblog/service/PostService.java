package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	
	public void insert(PostVo vo) {
		postRepository.insert(vo);
	}

	// 카테고리 별 포스트 개수 
	public int count(Long categoryNo) {
		return postRepository.count(categoryNo);
	}

}
