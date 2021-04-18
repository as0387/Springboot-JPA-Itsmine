package com.dongs.itsmine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dongs.itsmine.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/* @AuthenticationPrincipal PrincipalDetail principal */
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size=5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.게시글목록(pageable));
		return "index"; //viewResolver 작동!(이때 인덱스로 model의 정보를 들고 이동을 해줌.)
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.게시글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.게시글상세보기(id));
		return "board/updateForm";
	}
	
	@GetMapping({"/board/saveForm"})
	public String saveForm() {
		return "board/saveForm";
	}
}
