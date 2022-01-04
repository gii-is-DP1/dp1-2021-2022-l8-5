package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
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
	private PlayerService playerService;

	@Autowired
	public GameController(GameService gameService, PlayerService playerService) {
		this.gameService = gameService;
		this.playerService = playerService;
	}
	
	@GetMapping()
	public String listGames(ModelMap modelMap) {
		String view = "games/listGames";
		Iterable<Game> games = gameService.findAll();
		modelMap.addAttribute("games", games);
		return view;
	}
	
	
	@GetMapping(path="/{gameId}/delete")
	public String deleteGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap) {
		Optional<Game> game = gameService.findByGameId(gameId);
		
		if (game.isPresent()) {
			// only first player can delete a game
			if(this.amIFirstPlayer(game.get())) {
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
        Player loggedPlayer = LoggedUserController.loggedPlayer();
        
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
		Player currentPlayer = this.loggedPlayer();
		Integer currentGameId = gameService.getCurrentGameId(currentPlayer);
		Optional<Board> currentBoard =gameService.findBoardByGameId(currentGameId);
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
		Player loggedPlayer = LoggedUserController.loggedPlayer();
		
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
        Player loggedPlayer = LoggedUserController.loggedPlayer();
        
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
	
	private Player loggedPlayer() {
		String playerUsername = LoggedUserController.returnLoggedUserName();
		Player player = playerService.findPlayerByUserName(playerUsername);

		return player;
	}

    private Boolean amIFirstPlayer(Game game){
        Boolean amI = false;
        if(game.firstPlayer != null)
            amI = game.firstPlayer.getId() == LoggedUserController.loggedPlayer().getId();

        return amI;
    }
}
