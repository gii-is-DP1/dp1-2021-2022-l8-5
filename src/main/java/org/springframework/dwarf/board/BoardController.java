package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private PlayerService playerService;
    
    @Autowired
    private ApplicationContext applicationContext;

	@Autowired
	public BoardController(BoardService boardService, GameService gameService,
			ResourcesService resourcesService, WorkerService workerService, PlayerService playerService) {
		this.boardService = boardService;
        this.gameService = gameService;
        this.resourcesService = resourcesService;
        this.workerService = workerService;
        this.playerService = playerService;
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
    	response.addHeader("REFRESH", "5000");
    	String view = "/board/board";
    	
    	Game game = gameService.findByGameId(gameId).get();
    	Board board = boardService.findByBoardId(boardId).get();
    	
		String playerUsername = LoggedUserController.returnLoggedUserName();
		Player myplayer = playerService.findPlayerByUserName(playerUsername);
		
		Collection<Worker> myworkers = workerService.findByPlayerId(myplayer.getId());	
		for (int i = 0; i < myworkers.size(); i++) {
			Worker myworker = myworkers.stream().collect(Collectors.toList()).get(i);
			modelMap.addAttribute("myworker" + (i+1), myworker);
		}

    	modelMap.addAttribute("myplayer", myplayer);
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
    	
    	game.phaseResolution(this.applicationContext);
    	try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			
		}
    	
    	return view;
    }
    
    
    @PostMapping("{boardId}/game/{gameId}")
    public String postWorker(@ModelAttribute("myworker1") Worker myworker1, @PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, BindingResult result) {
		if (result.hasErrors()) {
			return "/board/board";
		}
		else {
			String username = LoggedUserController.returnLoggedUserName();
			Player player = playerService.findPlayerByUserName(username);
			List<Worker> workers = new ArrayList<Worker>(workerService.findByPlayerId(player.getId()));
			return updatingWorker(workers.get(0).getId(), myworker1);
		}
    	
    }
    
    
	private String updatingWorker(Integer workerId, Worker worker) {
		Worker workerFound = workerService.findByWorkerId(workerId).get();
		BeanUtils.copyProperties(worker, workerFound, "id", "player", "game", "status");
		this.workerService.saveWorker(workerFound);
		return "/board/board";

	}
}
