package org.springframework.dwarf.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Diego Ruiz Gil
 */
@Controller
@RequestMapping("/boards")
public class BoardController {
	private BoardService boardService;
    private GameService gameService;
    private MountainDeckService mountainDeckSer;

	@Autowired
	public BoardController(BoardService boardService, GameService gameService, MountainDeckService mountainDeckSer) {
		this.boardService = boardService;
        this.gameService = gameService;
        this.mountainDeckSer = mountainDeckSer;
	}
	
	@GetMapping()
	public String getBoard(ModelMap modelMap) {
		String view = "/board/board";
		Board board = boardService.findByBoardId(1).get();
		modelMap.addAttribute("board", board);
		return view;
	}

    @GetMapping("/game/{gameId}")
    public String initBoardGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap){
		Board board = new Board();

        Game game = gameService.findByGameId(gameId).get();
        MountainDeck mountainDeck = mountainDeckSer.findByMountainDeckId(1).get();
        
        board.setGame(game);
        board.setMountainDeck(mountainDeck);
        boardService.saveBoard(board);

		modelMap.addAttribute("board", board);
        
		String redirect = "redirect:/boards/"+board.getId()+"/game/"+game.getId();
	    return redirect;	
	}
    
    @GetMapping("{boardId}/game/{gameId}")
    public String boardGame(@PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, ModelMap modelMap) {
    	String view = "/board/board";
    	
    	return view;
    }
}
