package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	
	// 카테고리 별 포스트 개수(ADMIN-category)
	public int count(Long categoryNo) {
		return sqlSession.selectOne("post.getCount", categoryNo);
		
	}
	
	// 카테고리 별 포스트 목록(MAIN)
	public List<PostVo> getPostList(String id, Long categoryNo) {
		return sqlSession.selectOne("post.getCount", Map.of("id", id, "categoryNo", categoryNo));
	}
	
	// 글 보기(MAIN)
	public PostVo getPost(Long categoryNo, Long postNo) {
		return sqlSession.selectOne("post.getPost", Map.of("categoryNo", categoryNo, "postNo", postNo));
	}

}
