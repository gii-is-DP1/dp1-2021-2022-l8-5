package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    private boolean boardPageLoaded;
	
	@GetMapping()
	public String getBoard(ModelMap modelMap) {
		String view = "/board/board";
		Board board = boardService.findByBoardId(1).get();
		modelMap.addAttribute("board", board);
		return view;
	}

    @GetMapping("/game/{gameId}")
    public String initBoardGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap) throws IllegalPositionException{
    	Game game = gameService.findByGameId(gameId).get();
		Board board = boardService.createBoard(game);
		int i = 1;
		for (Player p : game.getPlayersList()) {
			resourcesService.createPlayerResource(p, game);
			workerService.createPlayerWorkers(p, game, i);
			i = i+1;
		}
		this.setTurns(game.getPlayersList());
		
		this.boardPageLoaded = false;
		
		String redirect = "redirect:/boards/"+board.getId()+"/game/"+gameId;
	    return redirect;
	    
	}
    
    private void setTurns(List<Player> players) {
    	Integer turn = 1;
    	for(Player player: players) {
    		player.setTurn(turn);
    		
    		try {
				playerService.savePlayer(player);
			} catch (DuplicatedUsernameException e) {
				e.printStackTrace();
			} catch (DuplicatedEmailException e) {
				e.printStackTrace();
			} catch (InvalidEmailException e) {
				e.printStackTrace();
			}
    		
    		turn ++;
    	}
    }
    
    @GetMapping("{boardId}/game/{gameId}")
    public String boardGame(@PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, ModelMap modelMap, HttpServletResponse response) {
    	response.addHeader("REFRESH", "4");
    	String view = "/board/board";
    
    	Game game = gameService.findByGameId(gameId).get();
    	Board board = boardService.findByBoardId(boardId).get();
    	
		String playerUsername = LoggedUserController.returnLoggedUserName();
		Player myplayer = playerService.findPlayerByUserName(playerUsername);
		
		List<Worker> myworkers = workerService.findNotPlacedByPlayerIdAndGameId(myplayer.getId(), gameId);
		
		if (myworkers.size()>=1) {
			Worker myworker = myworkers.get(0);
			modelMap.addAttribute("myworker", myworker);
		}

    	modelMap.addAttribute("myplayer", myplayer);
    	modelMap.addAttribute("board", board);
    	modelMap.addAttribute("game", game);
    	modelMap.addAttribute("phaseName", game.getCurrentPhaseName().toString());
    	
    	List<Integer> xpos = Arrays.asList(1,2,3);
    	List<Integer> ypos = Arrays.asList(0,1,2);
    	
    	modelMap.addAttribute("xpos", xpos);
    	modelMap.addAttribute("ypos", ypos);
    	
    	List<Worker> workers = new ArrayList<Worker>();
    	
    	for (int i = 0; i < game.getPlayersList().size(); i++) {
    		Player p = game.getPlayersList().get(i);
    		if (p != null) {
	        	modelMap.addAttribute("player" + (i+1), p);
	        	Resources resourcesPlayer = resourcesService.findByPlayerIdAndGameId(p.getId(), game.getId()).get();
	        	modelMap.addAttribute("resourcesPlayer" + (i+1), resourcesPlayer);
	        	workers.addAll(workerService.findByPlayerId(p.getId()));
    		}
    	}
    	modelMap.addAttribute("workers" , workers);
	
   		if (game.getCurrentPhaseName() != GamePhaseEnum.ACTION_SELECTION && this.boardPageLoaded) {
			game.phaseResolution(this.applicationContext);
		}
    	
    	try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			
		}
    	
    	if(!this.boardPageLoaded)
    		this.boardPageLoaded = true;
    	
    	return view;
    }
    
    
    @PostMapping("{boardId}/game/{gameId}")
    public String postWorker(@Valid Worker myworker, @RequestParam String pos, @PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, BindingResult result, Error errors) {
		Game game = gameService.findByGameId(gameId).get();
		game.phaseResolution(this.applicationContext);
		
		if (result.hasErrors()) {
			return "/board/board";
		}
		else {
			//parsear pos, settear al worker myworker las posiciones "x" e "y" obtenidas de pos
			String[] posxy = pos.trim().split(",");
			Integer posx = Integer.parseInt(posxy[0]);
			Integer posy = Integer.parseInt(posxy[1]);
			myworker.setXposition(posx);
			myworker.setYposition(posy);
			
			String username = LoggedUserController.returnLoggedUserName();
			Player player = playerService.findPlayerByUserName(username);
			List<Worker> workersNotPlaced = workerService.findNotPlacedByPlayerIdAndGameId(player.getId(), game.getId());
			
			return updatingWorker(workersNotPlaced.get(0).getId(), myworker, boardId, gameId, errors,result);
		}
    	
    }
    
    
	private String updatingWorker(Integer workerId, Worker worker, Integer boardId,Integer gameId, Error errors, BindingResult result) {
		
		String redirect = "redirect:/boards/"+ boardId +  "/game/"+gameId;
		Worker workerFound = workerService.findByWorkerId(workerId).get();
		BeanUtils.copyProperties(worker, workerFound, "id", "player", "game", "image");
		BoardCell boardCell = boardCellService.findByPosition(workerFound.getXposition(), workerFound.getYposition());
	
		if(boardCell.isCellOccupied()){
			return errors.getMessage();
		}
		//boardCell.setCellOccupied(true);
		boardCell.setOccupiedBy(LoggedUserController.loggedPlayer());
		workerFound.setStatus(true);
	//	b.setCellOccupied(true);
		try {
			this.workerService.saveWorker(workerFound);
		} catch (IllegalPositionException e) {
			result.rejectValue ("xposition", " invalid", "can't be empty");
			return "/board/board";
		}
		this.boardCellService.saveBoardCell(boardCell);
		this.boardService.saveBoard(boardService.findByBoardId(boardId).get());
		
	    
		
		return redirect;

	}
}
