## 스프링 3.x 버전으로 따라하시는 분들
```text
스프링 3.x 버전은 최소 JDK 버전이 17입니다.
```

### maven 의존성과 시큐리티 변경된 코드
브랜치 update-202403 을 참고하세요

```java
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
		http.csrf(c -> c.disable());

		// 2. 인증 주소 설정 (WEB-INF/** 추가해줘야 함. 아니면 인증이 필요한 주소로 무한 리다이렉션 일어남)
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/WEB-INF/**","/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll()
				.anyRequest().authenticated());
		
		// 3. 로그인 처리 프로세스 설정
		http.formLogin(f -> f.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/")
		);

		return http.build();
	}
}
```

### mysql 연결
```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database blog;
```

### gradle 의존성
```gradle
dependencies {
	implementation 'org.apache.tomcat:tomcat-jasper:11.0.0-M16'
	implementation 'jakarta.servlet:jakarta.servlet-api'
	implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api'
	implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl'

	//implementation 'org.springframework.boot:spring-boot-starter-security'
	//implementation 'org.springframework.security:spring-security-taglibs'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	
	runtimeOnly 'com.mysql:mysql-connector-j'
	
	// 테스트 DB 임시
	runtimeOnly 'com.h2database:h2'
	
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
}
```