package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 1. 어노테이션 제거
@Configuration
public class SecurityConfig{ // 2. extends 제거

	// 3. principalDetailService 제거

	// 4. AuthenticationManager 메서드 생성
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean // IoC가 되요!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 5. 기본 패스워드 체크가 BCryptPasswordEncoder 여서 설정 필요 없음.


	// 6. 최신 버전(2.7)으로 시큐리티 필터 변경
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 1. csrf 비활성화
		http.csrf().disable();

		// 2. 인증 주소 설정
		http.authorizeRequests(
				authorize -> authorize.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll()
						.anyRequest().authenticated()
		);
		
		// 3. 로그인 처리 프로세스 설정
		http.formLogin(f -> f.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/")
		);

		return http.build();
	}
}
