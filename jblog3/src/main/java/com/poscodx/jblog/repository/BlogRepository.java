package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;


	// 현재 사이트 정보 가져오기 (select) 
	public BlogVo find(String id) {
		return sqlSession.selectOne("blog.find", id);
	}

	// 사이트 정보 변경하기 (update)
	public Object update(BlogVo vo) {
		return sqlSession.update("blog.update", vo);
	}
	
	// 블로그 추가
	public void insert(String id) {
		sqlSession.insert("blog.insert", id);
	}
	
}
