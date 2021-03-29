package com.dongs.itsmine.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}