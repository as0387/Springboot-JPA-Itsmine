package com.dongs.itsmine.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@RequiredArgsConstructor//이건 변수에 final있는걸 생성자로 만들어줌
//불변성때문에 final로 한다. 변경할 일이있으면 final로 하면 안됨.
@Data
@NoArgsConstructor//빈 생성자
public class Member {
	private  int id;
	private  String user;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String user, String password, String email) {
		this.id = id;
		this.user = user;
		this.password = password;
		this.email = email;
	}
	
	
}
