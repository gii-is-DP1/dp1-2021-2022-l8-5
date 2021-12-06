package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.CorrentUserController;
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
        Player currentPlayer = this.currentPlayer();

        if(game.isPresent()){
        	gameService.exit(game.get(), currentPlayer);
        }else{
            modelMap.addAttribute("message", "game not found!");
        }

        return "redirect:/games/searchGames";
    }
	
	@GetMapping("/searchGames")
	public String searchGames(ModelMap modelMap) {
		String view  = "games/searchOrCreateGames";
		String currentPlayer = CorrentUserController.returnCurrentUserName();
		if (gameService.alreadyInGame(currentPlayer)){
			Integer currentGameId = gameService.getCurrentGameId(currentPlayer);
			return "redirect:/games/"+ currentGameId +"/waitingPlayers";
		}
		List<Game> gamesToJoin = gameService.findGamesToJoin();
		modelMap.addAttribute("gamesToJoin", gamesToJoin);
		return view;
	}
	
	@GetMapping(path="/waitingPlayers")
	public String initCreateGame(ModelMap modelMap) {
		Game game = new Game();
		Player currentPlayer = this.currentPlayer();
		
		game.setFirstPlayer(currentPlayer);
		game.setCurrentPlayer(currentPlayer);
			
		try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
        modelMap.addAttribute("currentPlayer", currentPlayer);
        
		return "redirect:/games/"+game.getId().toString()+"/waitingPlayers";
	}
	
	@GetMapping(path="{gameId}/waitingPlayers")
	public String joinGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap, ModelAndView modelAndView, HttpServletResponse response) {
		response.addHeader("Refresh", "2");
		String view = "games/waitingPlayers";
		
		Game game = gameService.findByGameId(gameId).get();
        Player currentPlayer = this.currentPlayer();
		
		try {
			gameService.joinGame(game, currentPlayer);
		} catch(CreateGameWhilePlayingException ex) {
			
			modelAndView.addObject("message", "games/searchOrCreateGames");
			//modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
		modelMap.addAttribute("currentPlayer", currentPlayer);

		return view;
	}
	
	private Player currentPlayer() {
		String playerUsername = CorrentUserController.returnCurrentUserName();
		Player player = playerService.findPlayerByUserName(playerUsername);
		
		return player;
	}

    private Boolean amIFirstPlayer(Game game){
        Boolean amI = false;
        if(game.firstPlayer != null)
            amI = game.firstPlayer.getId() == this.currentPlayer().getId();

        return amI;
    }
}
