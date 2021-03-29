package com.dongs.itsmine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dongs.itsmine.model.User;

//DAO(Data Access Object)
//자동으로 bean 등록이 된다.
//@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	//JPA Naming 전략
	//네이밍 전략에 맞춰 함수를 만들면 SELECT * FROM user WHERE username= ?1 AND password=?2; 요런쿼리가 동작
	User findByUsernameAndPassword(String username, String password);
	
	/*
	 * @Query(value="SELECT * FROM user WHERE username= ?1 AND password=?2",nativeQuery = true)
	 * User login(String username, String password);
	 * 요방법도 있음
	 */
}
