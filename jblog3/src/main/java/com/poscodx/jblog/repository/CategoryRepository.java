package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	SqlSession sqlSession;
	
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 카테고리 생성
	public void insert(CategoryVo vo) {
		sqlSession.insert("category.insert", vo);
		
	}

	public List<CategoryVo> getCategory(String id) {
		return sqlSession.selectList("category.getCategory", id);
	}

}
