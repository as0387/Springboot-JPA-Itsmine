package com.dongs.itsmine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongs.itsmine.model.RoleType;
import com.dongs.itsmine.model.User;
import com.dongs.itsmine.repository.UserRepository;

//스프링이 컴포넌트 스탠을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();//비번 원문
		String encPassword = encoder.encode(rawPassword);//해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User user) {
		//수정시엔 영속성 컨텍스트 User 오브젝트를 영속화 시키고
		// 영속화된 User 오브젝트를  수정하는 것이 좋다.
		//select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				});
		
		//Validate 체크(카카오나 뭐 그런 로그인시 수정 안되게함.
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();//비번 원문
			String encPassword = encoder.encode(rawPassword);//해쉬화
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = 커밋 자동으로 진행
		// 그말인직 영속화된 persistance 객체의 변화가 감지되면 (더티체킹) 자동으로 update문을 날려줌
	}
}
