package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			System.out.println("check auth!!!!!");
		
		//1. handler 종류 확인
				if(!(handler instanceof HandlerMethod)) {
					// 검사가 필요하지 않는 경우(asset, login, logout)
					return true;
				}
				
				//2. casting
				HandlerMethod handlerMethod = (HandlerMethod)handler;
				
				//3. Handler Method의 @Auth 가져오기 
				Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
				//4. Handler Method에 @Auth가 없는 경우
//				if(auth == null) {
//					auth = handlerMethod
//							.getMethod()
//							.getDeclaringClass()
//							.getAnnotation(Auth.class);
//				}
				
				if(auth == null) {
					return true;
				}
				
				//5. @Auth가 붙어있기 때문에 인증 여부 확인 -> Type 이나 Method 에 @Auth가 없는 경우 
				HttpSession session = request.getSession();
				UserVo authUser = (UserVo)session.getAttribute("authUser");
				
				if(authUser == null) {
					response.sendRedirect(request.getContextPath() + "/user/login");  // 비정상적인 접근일 경우, login 화면으로 리다이렉트
					return false;
				}
		
		return true;
	}

}
