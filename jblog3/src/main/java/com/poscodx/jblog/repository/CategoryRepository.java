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

	// 카테고리 목록 보기 
	public List<CategoryVo> getCategory(String id) {
		return sqlSession.selectList("category.getCategorySummary", id);
	}

	// 카테고리 삭제 
	public Object delete(Long no) {
		return sqlSession.delete("category.delete", no);
	}

	public Long currentCategory(String id) {
		return sqlSession.selectOne("category.currentCategoryNo", id);
	}

}
