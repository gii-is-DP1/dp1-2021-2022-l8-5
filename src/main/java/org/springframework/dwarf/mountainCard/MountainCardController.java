/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.dwarf.mountainCard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.game.GameService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pablo Álvarez
 * @author Pablo Marín
 */

@Controller
@RequestMapping("/boards/{boardId}/boardcards")
public class MountainCardController {
	
	private MountainCardService mountainCardService;
	private BoardService boardService;

	@Autowired
	public MountainCardController(MountainCardService mountainCardService, GameService gameService, BoardService boardService) {
		this.mountainCardService = mountainCardService;
		this.boardService = boardService;
		
	}

	@GetMapping()
	public String listMountainCards(@PathVariable("boardId") Integer boardId, ModelMap modelMap) {
		Board currentBoard = boardService.findByBoardId(boardId).get();
		List<BoardCell> currentBoardCells = currentBoard.getBoardCells();
		modelMap.addAttribute("boardCells", currentBoardCells);
		
		return "board/boardcards";
	}

}
