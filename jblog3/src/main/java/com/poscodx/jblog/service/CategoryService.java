package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	// 카테고리 생성 
	public void join(CategoryVo vo) {
		categoryRepository.insert(vo);
		
	}

	// 카테고리 내역
	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.getCategory(id);
	}

	// 카테고리 추가
//	public void insert(CategoryVo vo) {
//		categoryRepository.
//	}
	
	

}
