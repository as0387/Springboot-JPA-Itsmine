package com.dongs.itsmine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dongs.itsmine.model.Board;

//DAO(Data Access Object)
//자동으로 bean 등록이 된다.
//@Repository //생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
