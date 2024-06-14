package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	// 카테고리 생성 
	public void join(String id, String name) {
		categoryRepository.insert(id, name);
		
	}

}
