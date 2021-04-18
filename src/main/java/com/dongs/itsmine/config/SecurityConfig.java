package com.dongs.itsmine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dongs.itsmine.config.auth.PrincipalDetailService;

//Bean 등록 = 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다고 해주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailSerice;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // 이 함수의 리턴값이 IoC에 저장 된다.
	public BCryptPasswordEncoder encodePWD() {
		return  new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할수있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailSerice).passwordEncoder(encodePWD());
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception { // 그 등록된 필터를 이 메서드에서 설정한다.
		http
		.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어놓는게 좋음)
		.authorizeRequests()//리퀘스트가 들어오면
			.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")// /auth/**으로 들어오는 리퀘스트면
			.permitAll()// 누구나 들어올수 있다.
			.anyRequest()// 이외의 모든 요청은
			.authenticated() // 인증이되어야해
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당주소로 오는 로그인을 가로채서 대신 로그인을 해줌.
			.defaultSuccessUrl("/"); //인증이되면 띄워줄 페이지.
	}
}
