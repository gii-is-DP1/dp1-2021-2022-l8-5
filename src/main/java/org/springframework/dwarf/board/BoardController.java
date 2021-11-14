package org.springframework.dwarf.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Diego Ruiz Gil
 */
public class BoardController {
	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping()
	public String listBoard(ModelMap modelMap) {
		String view = "/board/listBoard";
		Iterable<Board> board = boardService.findAll();
		modelMap.addAttribute("board", board);
		return view;

	}
}
