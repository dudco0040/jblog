package com.poscodx.jblog.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.interceptor.BlogInterceptor;
import com.poscodx.jblog.security.AuthInterceptor;
import com.poscodx.jblog.security.LoginInterceptor;
import com.poscodx.jblog.security.LogoutInterceptor;
import com.poscodx.jblog.service.UserService;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
//	@Autowired
//	private UserService userService;
	
	// <!-- Interceptors -->
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public HandlerInterceptor AuthInterceptor() {
		return new AuthInterceptor();
	}
	
	@Bean
	public HandlerInterceptor BlogInterceptor() {
		return new BlogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registy) {
		registy
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		
		registy
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout");
		
		registy
			.addInterceptor(AuthInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth", "/user/logout", "/assets/**");
		
		registy
			.addInterceptor(BlogInterceptor())
			.addPathPatterns("/**");
	}

}
