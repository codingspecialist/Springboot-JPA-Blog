## 스프링 3.x 버전으로 따라하시는 분들
```text
스프링 3.x 버전은 최소 JDK 버전이 17입니다.
```

### maven 의존성과 시큐리티 변경된 코드
브랜치 update-202403 을 참고하세요

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