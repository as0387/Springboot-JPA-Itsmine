package com.dongs.itsmine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongs.itsmine.model.User;
import com.dongs.itsmine.repository.UserRepository;

//스프링이 컴포넌트 스탠을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 회원가입(User user) {
			userRepository.save(user);
	}

	@Transactional(readOnly = true) //Select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}
}
