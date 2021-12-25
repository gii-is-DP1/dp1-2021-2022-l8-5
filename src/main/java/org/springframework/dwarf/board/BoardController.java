package org.springframework.dwarf.board;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.worker.WorkerService;
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
    private ResourcesService resourcesService;
    private WorkerService workerService;

	@Autowired
	public BoardController(BoardService boardService, GameService gameService, ResourcesService resourcesService, WorkerService workerService) {
		this.boardService = boardService;
        this.gameService = gameService;
        this.resourcesService = resourcesService;
        this.workerService = workerService;
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
    	Game game = gameService.findByGameId(gameId).get();
		Board board = boardService.createBoard(game);
		for (Player p : game.getPlayersList()) {
			resourcesService.createPlayerResource(p, game);
			workerService.createPlayerWorkers(p, game);
		}
        
		String redirect = "redirect:/boards/"+board.getId()+"/game/"+gameId;
	    return redirect;
	    
	}
    
    @GetMapping("{boardId}/game/{gameId}")
    public String boardGame(@PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, ModelMap modelMap, HttpServletResponse response) {
    	response.addHeader("REFRESH", "5");
    	String view = "/board/board";
    	
    	Game game = gameService.findByGameId(gameId).get();
    	Board board = boardService.findByBoardId(boardId).get();

    	
    	modelMap.addAttribute("board", board);
    	modelMap.addAttribute("game", game);
    	
    	for (int i = 0; i < game.getPlayersList().size(); i++) {
    		Player p = game.getPlayersList().get(i);
    		if (p != null) {
	        	modelMap.addAttribute("player" + (i+1), p);
	        	Resources resourcesPlayer = resourcesService.findByPlayerIdAndGameId(p.getId(), game.getId()).get();
	        	modelMap.addAttribute("resourcesPlayer" + (i+1), resourcesPlayer);
    		}
    	}
    	
    	game.phaseResolution();
    	try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			
		}
    	
    	return view;
    }
}
