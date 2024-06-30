package com.poscodx.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

public class BlogInterceptor implements HandlerInterceptor {
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Locale
		System.out.println("resolver-locale: " + localeResolver.resolveLocale(request).getLanguage());	
		request.setAttribute("language", localeResolver.resolveLocale(request).getLanguage());
		
		return true;
	}

}
