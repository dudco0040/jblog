package com.poscodx.jblog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.poscodx.jblog.security.UserDetailsServiceImpl;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return new WebSecurityCustomizer() {
			@Override
			public void customize(WebSecurity web) {
				web
					.ignoring()
					.requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
					.requestMatchers(new AntPathRequestMatcher("/assets/**"));
			}
		};
	
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
	    	.logout()
	    	.logoutUrl("/user/logout")
	    	.logoutSuccessUrl("/")
	    	.and()
			
			.formLogin()
			.loginPage("/user/login")
			.loginProcessingUrl("/user/auth")
			.usernameParameter("id")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
//			.failureUrl("/user/login")
			.failureHandler(new AuthenticationFailureHandler() {
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					request.setAttribute("id", request.getParameter("id"));
					request
						.getRequestDispatcher("/user/login")
						.forward(request, response);
				}
       		})
			
			.and()
			
			.csrf()
			.disable();
			
		return http.build();
	}
	
	// Authentication Manager 설정
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		System.out.println("***" + userDetailsService + passwordEncoder);
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		
		return new ProviderManager(authenticationProvider);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
}
