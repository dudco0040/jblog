package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	private SqlSession sqlSession;
	
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 글 추가 
	public void insert(PostVo vo) {
		sqlSession.insert("post.insert", vo);
	}
	
	// 카테고리 별 포스트 개수 
	public int count(Long categoryNo) {
		return sqlSession.selectOne("post.getCount", categoryNo);
		
	}

}
