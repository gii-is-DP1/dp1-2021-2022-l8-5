package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GamePhaseEnum;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
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
			//modelMap.addAttribute("imgPlayer" + i, i);
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
			} catch (DataAccessException | DuplicatedUsernameException | DuplicatedEmailException
					| InvalidEmailException e1) {
				e1.printStackTrace();
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
		Player myplayer = LoggedUserController.loggedPlayer();
		
		modelMap = this.setHasEnoughBadges(modelMap, myplayer.getId(), gameId);
		
		modelMap = this.setMyWorkerForPost(modelMap, myplayer.getId(), gameId);
		modelMap = this.hasAidWorkers(modelMap, gameId);

    	modelMap.addAttribute("myplayer", myplayer);
    	modelMap.addAttribute("board", board);
    	modelMap.addAttribute("game", game);
    	modelMap.addAttribute("phaseName", game.getCurrentPhaseName().toString());
    	
    	modelMap.addAttribute("xpos", List.of(1,2,3));
    	modelMap.addAttribute("ypos", List.of(0,1,2));
    	
    	modelMap = this.setPlayersData(modelMap, game);
	
   		if (game.getCurrentPhaseName() != GamePhaseEnum.ACTION_SELECTION && this.boardPageLoaded) {
			game.phaseResolution(this.applicationContext);
		}
    	
   		try {
			gameService.saveGame(game);
		} catch (DataAccessException | CreateGameWhilePlayingException e) {
			e.printStackTrace();
		}
    	
    	if(!this.boardPageLoaded)
    		this.boardPageLoaded = true;
    	
    	return view;
    }
    
    private ModelMap setHasEnoughBadges(ModelMap modelMap, int pid, int gid) {
    	Resources myresources = resourcesService.findByPlayerIdAndGameId(pid, gid).get();
		modelMap.addAttribute("canPay", myresources.getBadges()>=4);
    	return modelMap;
    }
    
    private ModelMap setMyWorkerForPost(ModelMap modelMap, int pid, int gid) {
    	List<Worker> myworkers = workerService.findNotPlacedByPlayerIdAndGameId(pid, gid);
		if (myworkers.size()>=1) {
			Worker myworker = myworkers.get(0);
			modelMap.addAttribute("myworker", myworker);
		}
    	return modelMap;
    }
    
    private ModelMap setPlayersData(ModelMap modelMap, Game game) {
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
    	
    	return modelMap;
    }
    
    private ModelMap hasAidWorkers(ModelMap modelMap, int gameId) {
    	List<Worker> aidWorkersNotPlaced = workerService.findNotPlacedAidByGameId(gameId);
    	modelMap.addAttribute("hasAidWorkers", !aidWorkersNotPlaced.isEmpty());
    	return modelMap;
    }
    
    
    @PostMapping("{boardId}/game/{gameId}")
    public String postWorker(@Valid Worker myworker, @RequestParam String pos, @RequestParam(required = false) String pay, @PathVariable("gameId") Integer gameId, @PathVariable("boardId") Integer boardId, BindingResult result, Error errors) {
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
			
			if (posx == 0) {
				Boolean useBadges = (pay==null) ? false : true;
				return updatingWorkerSpecial(workersNotPlaced, myworker, useBadges, boardId, gameId, errors,result);
			} else {
				return updatingWorker(workersNotPlaced.get(0), myworker, boardId, gameId, errors,result);
			}
			
		}
    	
    }
    
    private String updatingWorkerSpecial(List<Worker> workers, Worker myworker, Boolean useBadges, Integer boardId, Integer gameId, Error errors, BindingResult result) {
    	String redirect = "redirect:/boards/"+ boardId +  "/game/"+gameId;
    	
    	if (useBadges) {
    		Worker workerFound = workers.get(0);
    		redirect = updateWorker(myworker, workerFound, result, redirect);
    		
    		Player player = workerFound.getPlayer();
    		Resources r = resourcesService.findByPlayerIdAndGameId(player.getId(), gameId).get();
    		
    		try {
				r.setResource(ResourceType.BADGES, -4);
				resourcesService.saveResources(r);
			} catch (Exception e) {
				e.printStackTrace();
			}

    	} else {
        	for (Worker workerFound : workers) {
        		redirect = updateWorker(myworker, workerFound, result, redirect);
        	}
    	}

    	//ejecutar la action
    	
    	return redirect;
    }
    
    private String updateWorker(Worker myworker, Worker workerFound, BindingResult result, String redirect) {
		BeanUtils.copyProperties(myworker, workerFound, "id", "player", "game", "image");
		workerFound.setStatus(true);
		try {
			this.workerService.saveWorker(workerFound);
		} catch (IllegalPositionException e) {
			result.rejectValue ("xposition", " invalid", "can't be empty");
			return "/board/board";
		}
		return redirect;
    }
    
	private String updatingWorker(Worker workerFound, Worker myworker, Integer boardId, Integer gameId, Error errors, BindingResult result) {
		
		String redirect = "redirect:/boards/"+ boardId +  "/game/"+gameId;
		redirect = updateWorker(myworker, workerFound, result, redirect);
		BoardCell boardCell = boardCellService.findByPosition(workerFound.getXposition(), workerFound.getYposition());
	
		if(boardCell.isCellOccupied()){
			return errors.getMessage();
		}

		boardCell.setOccupiedBy(LoggedUserController.loggedPlayer());
		this.boardCellService.saveBoardCell(boardCell);
		this.boardService.saveBoard(boardService.findByBoardId(boardId).get());
		
		return redirect;

	}
}
