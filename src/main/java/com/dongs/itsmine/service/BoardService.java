package com.dongs.itsmine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongs.itsmine.dto.ReplySaveRequestDto;
import com.dongs.itsmine.model.Board;
import com.dongs.itsmine.model.Reply;
import com.dongs.itsmine.model.User;
import com.dongs.itsmine.repository.BoardRepository;
import com.dongs.itsmine.repository.ReplyRepository;
import com.dongs.itsmine.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//스프링이 컴포넌트 스탠을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
@RequiredArgsConstructor // 초기화 안된 해들 초기화해주셈.
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;

	@Transactional
	public void 글쓰기(Board board, User user) { //title , content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 게시글목록(Pageable pageable) { //title , content
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 게시글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 게시글삭제하기(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 게시글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글찾기 실패: 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티채킹 - 자동 업데이트(db 쪽으로 flush)
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		/*
		 * User user = userRepository.findById(replySaveRequestDto.getUserId())
		 * .orElseThrow(()->{ return new
		 * IllegalArgumentException("댓글쓰기 실패: 작성자 Id를 찾을수 없습니다."); }); //영속화 완료
		 * 
		 * Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
		 * .orElseThrow(()->{ return new
		 * IllegalArgumentException("댓글쓰기 실패: 게시글 Id를 찾을수 없습니다."); }); //영속화 완료
		 * 
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replySaveRequestDto.getContent()) .build();
		 */
		
		/*
		 * Reply reply = new Reply(); reply.update(user, board,
		 * replySaveRequestDto.getContent());
		 * 	replyRepository.save(reply);
		 */
		
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());

	}

	@Transactional
	public void 댓글삭제하기(int replyId) {
		replyRepository.deleteById(replyId);		
	}
}
