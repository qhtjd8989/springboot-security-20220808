package com.study.security_bosung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.security_bosung.config.auth.AuthFailuerHandler;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter를 비활성 시키고 현재 시큐리티 설정을 따르겠다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() { // 암호화
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // csrf 요청 위조 
		http.authorizeRequests() // 인증관련된 세팅
		
			.antMatchers("/api/v1/grant/test/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			
			.antMatchers("/api/v1/grant/test/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			
			.antMatchers("/api/v1/grant/test/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			
			.antMatchers("/", "/index", "/mypage/**") // 우리가 지정한 요청
			.authenticated() // 인증을 거쳐라
			
			.anyRequest() // 다른 모든요청은
			.permitAll() // 모두 접근 권한을 부여한다 
			
			.and() // 설정 추가 
			
			.formLogin() // 로그인 방식은 form로그인을 사용하겠다
			.loginPage("/auth/signin") // 로그인 페이지는 해당 get요청을 통해 접근한다  해당 주소로 유도해주는 명령
			.loginProcessingUrl("/auth/signin") // 로그인 요청(post), controller: Postmapping
			.failureHandler(new AuthFailuerHandler())
			.defaultSuccessUrl("/");
		
		/*
		 * antMatchers로 접속하면 loginPage로 보낸다
		 * 
		 * 로그인을 성공하면 defaultSuccessUrl로 보낸다
		 */
	}
}
