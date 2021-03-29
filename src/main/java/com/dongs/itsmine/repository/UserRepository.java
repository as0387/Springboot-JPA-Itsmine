package com.dongs.itsmine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongs.itsmine.model.User;

//DAO(Data Access Object)
//자동으로 bean 등록이 된다.
//@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{

}
