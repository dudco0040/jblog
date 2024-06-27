package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	// 회원가입
	@Transactional
	public void join(UserVo vo) {
		System.out.println("### join info " + vo);
//		System.out.println("### password encoding... " + passwordEncoder.encode(vo.getPassword()));
		// 유저 생성
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		userRepository.insert(vo);
		
		// 블로그 생성
		blogRepository.insert(vo.getId());
		
		// 카테고리 생성
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setId(vo.getId());
		categoryVo.setName("first category");
		categoryVo.setDescription("welcome! " + vo.getName());
		categoryRepository.insert(categoryVo);
		
	}

	// AuthInterceptor
	public UserVo getUser(String id, String password) {
		return userRepository.findByNoAndPassword(id, password);
	}

	// 중복 ID 확인  
	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}

}
