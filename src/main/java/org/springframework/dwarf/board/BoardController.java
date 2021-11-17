package org.springframework.dwarf.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Diego Ruiz Gil
 */
@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
/*
	@GetMapping()
	public String listBoard(ModelMap modelMap) {
		String view = "/board/board";
		Iterable<Board> board = boardService.findAll();
		modelMap.addAttribute("boards", board);
		return view;

	}*/
	
	@GetMapping()
	public String getBoard(ModelMap modelMap) {
		String view = "/board/board";
		Board board = boardService.findByBoardId(1).get();
		modelMap.addAttribute("board", board);
		return view;

	}
}
