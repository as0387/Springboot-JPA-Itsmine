package com.dongs.itsmine.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일) = @Controller

@RestController //사용자가 요청 -> 응답(Data)
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m  = Member.builder().user("ssar").password("1234").email("as0387naver.com").build();
		System.out.println(TAG + "getter : " + m.getUser());
		m.setUser("dongs");
		System.out.println(TAG + "setter : " + m.getUser());
		return "lombok test 완료"; 
	}
	
	@GetMapping("/http/get") // http://localhost:8080/http/get
	public String getTest(Member m) {
		return "get 요청 : " + m.getId() + ", " + m.getUser() + ", " + m.getPassword() +", " + m.getEmail();
	}
	@PostMapping("/http/post")// http://localhost:8080/http/post
	public String postTest(@RequestBody Member m) {
		return "post 요청 : " + m.getId() + ", " + m.getUser() + ", " + m.getPassword() +", " + m.getEmail();
	}
	@PutMapping("/http/put")// http://localhost:8080/http/put
	public String putTest() {
		return "put 요청";
	}
	@DeleteMapping("/http/delete")// http://localhost:8080/http/delete
	public String deleteTest() {
		return "delete 요청";
	}
}
