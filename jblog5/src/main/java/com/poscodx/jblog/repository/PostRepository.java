package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

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
	public int count(Long categoryNoValue) {
		return sqlSession.selectOne("post.getCount", categoryNoValue);
		
	}
	
	// 카테고리 별 포스트 목록(MAIN)
	public List<PostVo> getPostList(Long categoryNo) {
		return sqlSession.selectList("post.getList", categoryNo);
	}
	
	// 글 보기(MAIN)
	public PostVo getPost(Long categoryNo, Long postNo) {
		return sqlSession.selectOne("post.getPost", Map.of("categoryNo", categoryNo, "postNo", postNo));
	}

	// 최근 게시물 번호 가져오기 
	public Long currentPost(Long currentCategoryNo) {
		return sqlSession.selectOne("post.currentPostNo", currentCategoryNo);
	}

	// 최근 글 불러오기 
	public PostVo getRecentPost(Long categoryNoValue) {
		return sqlSession.selectOne("post.getRecentPost", categoryNoValue);
	}

}
