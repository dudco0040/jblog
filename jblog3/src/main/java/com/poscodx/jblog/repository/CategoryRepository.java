package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {
	SqlSession sqlSession;
	
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 카테고리 생성
	public void insert(String id, String name) {
		sqlSession.insert("category.insert", Map.of("id", id, "name", name));
		
	}

}
