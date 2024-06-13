package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	// 회원가입
	public void join(UserVo vo) {
		userRepository.insert(vo);
	}

	// AuthInterceptor
	public UserVo getUser(String id, String password) {
		
		return null;
	}

}
