package com.dongs.itsmine.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongs.itsmine.model.RoleType; 
import com.dongs.itsmine.model.User;
import com.dongs.itsmine.repository.UserRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@RestController//html 파일이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	//http://localhost:8000/itsmine/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4를 찾으면 내가 db에서 못찾아오게 되면 user가 null이 되잖아?
		//그럼 return null 이 리턴이 되자나 그럼프로그램에 문제가 있지 않겠니?
		//Optional로 너의 user 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해
		
		//기본
		/*
		 * User user = userRepository.findById(id).orElseThrow(new
		 * Supplier<IllegalArgumentException>() {
		 * 
		 * @Override public IllegalArgumentException get() { return new
		 * IllegalArgumentException("해당 유저는 없습니다. id: " + id); } }); return user;
		 */
		//람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다.");
		});
		//요청: 웹브라우저
		//user객체 = java오브젝트 를 인식못함
		//따라서 웹브라우저가 이해할수있는 데이터 ->json
		//스프링 부트 = MessageConverter라는 애가 응답시에 자동작동
		//만약에 자바 오브젝트를 리턴하게되면 MessageConverter가 Jackson라이브러리를 호출해서
		//user오브젝트를 json으로 변환해서 웹브라우저에 던져준다.
			return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);		
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해달 id는 DB에 없습니다. id: " + id;
		}
		return "삭제 되었습니다. id: " + id;
	}
	
	@Transactional //을 사용하면 save 사용하지 않아도 update할수있다.(함수 종료시 자동으로 commit)
	@PutMapping("/dummy/user/{id}")
	 public User updateUser(@PathVariable int id, @RequestBody User requesUser) {
		 System.out.println("id: " +id);
		 System.out.println("password: " + requesUser.getPassword());
		 System.out.println("email: " +requesUser.getEmail());
		 
		 User user = userRepository.findById(id).orElseThrow(()->{
			 return new IllegalArgumentException("수정에 실패하였습니다.");
		 });
		 user.setPassword(requesUser.getPassword());
		 user.setEmail(requesUser.getEmail());
		 
		 //더티체킹 : 영속화된 데이터에 변경이 일어난것을 Transction이 감지하면 db에 수정이 일어나는 것
		 return user;
	 }
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();//전체 user객체 리턴
	}
	
	//한페이지당 두건의 데이터 리턴(페이징)
	@GetMapping("/dummy/user")
	public List<User> pagelist(@PageableDefault(size = 2, sort = "id",direction = Direction.DESC) Pageable pageable) {
		Page<User> pagingUser =  userRepository.findAll(pageable);
		
		/*
		 * if(pagingUser.isLast()) {//isFrist 등등 분기처리
		 * 
		 * }
		 */
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	//http://localhost:8000/itsmine.dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고(요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("rolle: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		user.setRole(RoleType.USER);
		userRepository.save(user); //save함수는 전달할 필드에대한 데이터가 있으면 update해주고 없으면 insert해준다.
		return "회원가입이 완료되었습니다.";
	}
}
