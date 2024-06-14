package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

@Repository
public class UserRepository {
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 회원가입 
	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}

	public UserVo findByNoAndPassword(String id, String password) {
		System.out.println("## id, password: "+ id + ", " + password);
		return sqlSession.selectOne("user.findByNoAndPassword", Map.of("id", id, "password", password));
	}

	public UserVo getUser(String id) {
		
		return sqlSession.selectOne("user.findByNo", id);
	}

}
