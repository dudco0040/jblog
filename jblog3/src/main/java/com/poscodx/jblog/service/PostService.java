package com.poscodx.jblog.service;

import java.util.List;
import java.util.Optional;

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
	public int count(Long categoryNoValue) {
		return postRepository.count(categoryNoValue);
	}
	
	// 카테고리 별 포스트 목록
	public List<PostVo> getPostList(Long categoryNo) {
		return postRepository.getPostList(categoryNo);
	}

	// 포스트 가져오기 
	public PostVo getPost(Long categoryNo, Long postNo) {
		return postRepository.getPost(categoryNo, postNo);
	}

}
