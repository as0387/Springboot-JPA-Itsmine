package com.dongs.itsmine.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //ai
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리를 쓸건데 그대 html 태그가 섞여서 글자 용량이 큼
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne // Many가 Board, User = One 즉 한명의 user는 여래가의 글을 작성할 수 있다.
	@JoinColumn(name="userId")
	private User user; // DB는 오브젝트를 정장할 수 없다. 따라서 FK를 사용, 하지만 자바는 오브젝트를 저장할수있다.
										//따라서 원래는 자바에서 DB에 맞는 자료형으로 저장해야했다.
										// 하지만 JPA를 사용하면 오브젝트를 그대로 저장할 수 있다.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)//하나의 게시글에는 여러개의 댓글이 달릴수있음 ,
																	//mappedBy 연관관계의 주인이아니다라는 의미를 가짐  DB에 칼럼을 만들지마세요!, ""안의 값은 reply의 프로퍼티명을 써주면된다.
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
