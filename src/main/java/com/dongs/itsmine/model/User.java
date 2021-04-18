package com.dongs.itsmine.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴
@Entity // 자동으로 User 클래스가 MySQL 테이블에 생성이된다.
//@DynamicInsert//insert시에 null인 필드를 제외시켜준다.
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY : 프로젝트에서 연결된 DB(오라클인가 MySQL인가에 따라)의 넘버링 전략을 따라간다.
	private int id;// 시퀀스, auto_increment로 넘버링

	@Column(nullable = false, length = 100, unique = true) // unique = 중복된 값 안되게 해줌.
	private String username;// 아이디

	@Column(nullable = false, length = 100)
	private String password;// 비밀번호

	@Column(nullable = false, length = 50)
	private String email;

	//@ColumnDefault("user")
	@Enumerated(EnumType.STRING) // DB는 Role타입이 없기때문에 알려줘야함.
	private RoleType role; // Enum을 쓰는게 좋다.(도메인(어떠한 범위)을 정할수있다.) //유저의 권한같은걸 줄수있다. ADMIN. USER
	
	private String oauth;// kakao, google

	@CreationTimestamp // 시간이 자동으로 입력된다.
	private Timestamp createDate;// 가입 날짜

}
