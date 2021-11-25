package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.CorrentUserController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		String redirect = "redirect:/games/searchGames";
		Optional<Game> game = gameService.findByGameId(gameId);
		if (game.isPresent()) {
			if(game.get().getFirstPlayer().equals(this.currentPlayer())) {
				gameService.delete(game.get());
				modelMap.addAttribute("message", "game deleted!");
			}else {
				return "redirect:/games/"+game.get().getId()+"/exit";
			}
		} else {
			modelMap.addAttribute("message", "game not found!");
		}
		return redirect;
	}

    @GetMapping(path="/{gameId}/exit")
    public String exitGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap){
        String redirect = "redirect:/games/searchGames";
        Optional<Game> game = gameService.findByGameId(gameId);
        Player player = this.currentPlayer();

        if(game.isPresent()){
            // the first player must delete the game when exit (deleteGame function)
            if(!this.amIFirstPlayer(game.get())){
                if(this.amISecondPlayer(game.get())){
                    game.get().setSecondPlayer(null);
                }else{
                    game.get().setThirdPlayer(null);
                }
                
                // HAY QUE TESTEARLO
                try {
        			gameService.saveGame(game.get());
        		} catch(CreateGameWhilePlayingException ex) {
        			return "redirect:/games/searchGames";
        		}
            }
        }else{
            modelMap.addAttribute("message", "game not found!");
        }

        return redirect;
    }
	
	@GetMapping("/searchGames")
	public String searchGames(ModelMap modelMap) {
		String view  = "games/searchOrCreateGames";
		List<Game> gamesToJoin = gameService.findGamesToJoin();
		modelMap.addAttribute("gamesToJoin", gamesToJoin);
		return view;
	}
	
	@GetMapping(path="/waitingPlayers")
	public String initCreateGame(ModelMap modelMap) {
		Game game = new Game();
		
		Player player = this.currentPlayer();
		
		game.setFirstPlayer(player);
		game.setCurrentPlayer(player);
			
		try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
        modelMap.addAttribute("currentPlayer", player);
        
		String redirect = "redirect:/games/"+game.getId().toString()+"/waitingPlayers";
		return redirect;
	}
	
	@GetMapping(path="{gameId}/waitingPlayers")
	public String joinGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap, HttpServletResponse response) {
		response.addHeader("Refresh", "2");
		String view = "games/waitingPlayers";
		
		Game game = gameService.findByGameId(gameId).get();
        Player player = this.currentPlayer();
        
        // HAY QUE TESTEARLO
        // if the player is in the game, he/she will just rejoin
        if(!game.isPlayerInGame(player)) {
        	if(game.secondPlayer == null)
        		game.setSecondPlayer(player);
        	else if(game.thirdPlayer == null)
        		game.setThirdPlayer(player);
        }
		
		try {
			gameService.saveGame(game);
		} catch(CreateGameWhilePlayingException ex) {
			modelMap.addAttribute("message", "You are already in another game");
			return "redirect:/games/searchGames";
		}
		
		modelMap.addAttribute("game", game);
		modelMap.addAttribute("currentPlayer", player);

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

    private Boolean amISecondPlayer(Game game){
        Boolean amI = false;
        if(game.secondPlayer != null)
            amI = game.secondPlayer.getId() == this.currentPlayer().getId();
        return amI;
    }

    private Boolean amIThirdPlayer(Game game){
		Boolean amI=false;
		if(game.thirdPlayer != null)
		amI = game.thirdPlayer.getId() == this.currentPlayer().getId();
        return amI;
    }
}
