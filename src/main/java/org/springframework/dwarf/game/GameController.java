package org.springframework.dwarf.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Diego Ruiz Gil
 * @author Francisco Javier Migueles Domínguez
 */

@Controller
@RequestMapping("/games")
public class GameController {
	
	private GameService gameService;
	private ResourcesService resourcesService;
	private LoggedUserController loggedUserController;

	@Autowired
	public GameController(GameService gameService, ResourcesService resourcesService, LoggedUserController loggedUserController) {
		this.gameService = gameService;
		this.resourcesService = resourcesService;
		this.loggedUserController=loggedUserController;
	}
	
	@GetMapping("/listGames/finished")
	public String listFinishedGames(ModelMap modelMap) {
		String view = "games/listGames";
		
		List<Game> games = gameService.findFinishedGames();
		List<Integer> indices = new ArrayList<Integer>();
		for(int i=0; i<games.size(); i++)
			indices.add(i);
		
		modelMap.addAttribute("indices", indices);
		modelMap.addAttribute("games", games);
		modelMap.addAttribute("type", "Finished");
		return view;
	}
	
	@GetMapping("/listGames/current")
	public String listCurrentGames(ModelMap modelMap) {
		String view = "games/listGames";
		
		List<Game> games = gameService.findCurrentGames();
		List<Integer> boardsId = games.stream()
				.map(game -> gameService.findBoardByGameId(game.getId()).get().getId())
				.collect(Collectors.toList());

		List<Integer> indices = new ArrayList<Integer>();
		for(int i=0; i<games.size(); i++)
			indices.add(i);
		
		modelMap.addAttribute("indices", indices);
		modelMap.addAttribute("games", games);
		modelMap.addAttribute("boardsId", boardsId);
		modelMap.addAttribute("type", "Current");
		return view;
	}
	
	@GetMapping(path="/{gameId}/delete")
	public String deleteGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap) {
		Optional<Game> game = gameService.findByGameId(gameId);
		
		if (game.isPresent()) {
			// only the last player can delete a game
			if(game.get().getPlayersList().size() <= 1) {
				gameService.delete(game.get());
				modelMap.addAttribute("message", "game deleted!");
			}
		} else {
			modelMap.addAttribute("message", "game not found!");
		}
		
		return "redirect:/games/searchGames";
	}

    @GetMapping(path="/{gameId}/exit")
    public String exitGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap){
        Optional<Game> game = gameService.findByGameId(gameId);
        Player loggedPlayer = loggedUserController.loggedPlayer();
        
        if(game.isPresent()){
        	gameService.exit(game.get(), loggedPlayer);
        }else{
            modelMap.addAttribute("message", "game not found!");
        }

        return "redirect:/games/searchGames";
    }
	
	@GetMapping("/searchGames")
	public String searchGames(ModelMap modelMap) {
		String view  = "games/searchOrCreateGames";
		Player currentPlayer = loggedUserController.loggedPlayer();
		Integer currentGameId = gameService.getCurrentGameId(currentPlayer);
		Optional<Board> currentBoard = gameService.findBoardByGameId(currentGameId);
		if (gameService.alreadyInGame(currentPlayer) && currentBoard.isPresent()){			
			return "redirect:/boards/"+ currentBoard.get().getId() +"/game/"+currentGameId;
		}

		else if (gameService.alreadyInGame(currentPlayer)){			
			return "redirect:/games/"+ currentGameId +"/waitingPlayers";
		}
		List<Game> gamesToJoin = gameService.findGamesToJoin();
		modelMap.addAttribute("gamesToJoin", gamesToJoin);
		return view;
	}
	
	@GetMapping(path="/waitingPlayers")
	public String initCreateGame(ModelMap modelMap) {
		Game game = new Game();
		Player loggedPlayer = loggedUserController.loggedPlayer();
		
		game.setFirstPlayer(loggedPlayer);
		game.setCurrentPlayer(loggedPlayer);
			
		try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
        modelMap.addAttribute("loggedPlayer", loggedPlayer);
        
		return "redirect:/games/"+game.getId().toString()+"/waitingPlayers";
	}
	
	@GetMapping(path="{gameId}/waitingPlayers")
	public String joinGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap, ModelAndView modelAndView, HttpServletResponse response) {
		response.addHeader("Refresh", "2");
		
		String view = "games/waitingPlayers";
		
		Game game = gameService.findByGameId(gameId).get();
        Player loggedPlayer = loggedUserController.loggedPlayer();
        
        // if first player started the game, go to board
        Optional<Board> board = gameService.findBoardByGameId(gameId);
        if(board.isPresent()) {
        	return "redirect:/boards/" + board.get().getId() + "/game/" + game.getId();
        }
		
		try {
			gameService.joinGame(game, loggedPlayer);
		} catch(CreateGameWhilePlayingException ex) {
			
			modelAndView.addObject("message", "games/searchOrCreateGames");
			//modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
		modelMap.addAttribute("loggedPlayer", loggedPlayer);
		

		return view;
	}
	
	@GetMapping(path="{gameId}/gameClassification")
	public String gameClassication(@PathVariable("gameId") Integer gameId, ModelMap modelMap) {
		String view = "games/gameClassification";
		
		Game game = gameService.findByGameId(gameId).get();
		modelMap = this.setPlayersData(modelMap, game);
		
		try {
			gameService.finishGame(game);
		} catch (DataAccessException | CreateGameWhilePlayingException e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	private ModelMap setPlayersData(ModelMap modelMap, Game game) {
    	
    	for (int i = 0; i < game.getPlayersList().size(); i++) {
    		Player p = game.getPlayersList().get(i);
    		if (p != null) {
	        	modelMap.addAttribute("player" + (i+1), p);
	        	Resources resourcesPlayer = resourcesService.findByPlayerIdAndGameId(p.getId(), game.getId()).get();
	        	modelMap.addAttribute("resourcesPlayer" + (i+1), resourcesPlayer);
    		}
    	}
    	
    	return modelMap;
    }
}
