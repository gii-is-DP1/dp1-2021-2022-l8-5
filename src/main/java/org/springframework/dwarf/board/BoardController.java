package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GamePhaseEnum;
import org.springframework.dwarf.game.GameService;
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
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private GameService gameService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BoardCellService boardCellService;
    
    @Autowired
    private ApplicationContext applicationContext;
	
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

	
   		if (game.getCurrentPhaseName() != GamePhaseEnum.ACTION_SELECTION) {
			game.phaseResolution(this.applicationContext);//la linea 
			//ayuda se está haciendo más poderosa
		}
    	
    	
    	
    	try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			
		}
    	
    	return view;
    }
    
    
    @PostMapping("{boardId}/game/{gameId}")
    public String postWorker(@ModelAttribute("myworker1") Worker myworker1, @PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, BindingResult result,Error errors) {
		Game game = gameService.findByGameId(gameId).get();
		game.phaseResolution(this.applicationContext); //la linea 2
		
		if (result.hasErrors()) {
			return "/board/board";
		}
		else {
			String username = LoggedUserController.returnLoggedUserName();
			Player player = playerService.findPlayerByUserName(username);
			List<Worker> workersNotPlaced = workerService.findNotPlacedByPlayerIdAndGameId(player.getId(), game.getId());
			
			return updatingWorker(workersNotPlaced.get(0).getId(), myworker1, boardId, gameId, errors);
		}
    	
    }
    
    
	private String updatingWorker(Integer workerId, Worker worker, Integer boardId,Integer gameId, Error errors) {
		
		String redirect = "redirect:/boards/"+ boardId +  "/game/"+gameId;
		Worker workerFound = workerService.findByWorkerId(workerId).get();
		BeanUtils.copyProperties(worker, workerFound, "id", "player", "game");
		BoardCell boardCell = boardCellService.returnBoardCell(workerFound.getXposition(), workerFound.getYposition());
	
		if(boardCell.getCellOccupied()){
			return errors.getMessage();
		}
		boardCell.setCellOccupied(true);
		workerFound.setStatus(true);
	//	b.setCellOccupied(true);
		this.workerService.saveWorker(workerFound);
		this.boardCellService.saveBoardCell(boardCell);
		
	    
		
		return redirect;

	}
}
