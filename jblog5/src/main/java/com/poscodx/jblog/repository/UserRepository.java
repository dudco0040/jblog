package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
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

//	// 중복 ID 확인
//	public UserVo findById(String id) {
//		return sqlSession.selectOne("user.findId", id);
//	}
//
//	// 인증 ID 확인 
//	public UserDetails findById2(String id) {
//		return sqlSession.selectOne("user.findId2", id);
//	}
	
	public <R> R findById(String id, Class<R> resultType) {
		FindByIdResultHandler<R> findByIdResultHandler = new FindByIdResultHandler<>(resultType);
		sqlSession.select("user.findId", id, findByIdResultHandler);

		return findByIdResultHandler.result;
	}

	private class FindByIdResultHandler<R> implements ResultHandler<Map<String, Object>> {
		private R result;
		private Class<R> resultType;

		FindByIdResultHandler(Class<R> resultType) {
			this.resultType = resultType;
		}

		@Override
		public void handleResult(ResultContext<? extends Map<String, Object>> resultContext) {
			Map<String, Object> resultMap = resultContext.getResultObject();
			result = new ObjectMapper().convertValue(resultMap, resultType);
		}
	}

}
