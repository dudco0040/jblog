package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	// 회원가입
	@Transactional
	public void join(UserVo vo) {
		// user insert
		userRepository.insert(vo);
	

		// user 추가가 완료된 경우, 블로그 생성과 카테고리 생성 
//		System.out.println("========" + vo);
//		if(userRepository.getUser(vo.getId()) != null) {;
//			// blog insert
//			System.out.println("회원가입이 성공적으로 완료되었습니다. ");
//			blogRepository.insert(vo.getId());
//		}
		
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
