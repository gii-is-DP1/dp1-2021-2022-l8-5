package org.springframework.dwarf.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.CorrentUserController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
			gameService.delete(game.get());
			modelMap.addAttribute("message", "game deleted!");
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
            if(game.get().firstPlayer.getId() != player.getId()){
                if(game.get().secondPlayer.getId() == player.getId()){
                    game.get().setSecondPlayer(null);
                }else{
                    game.get().setThirdPlayer(null);
                }
                gameService.saveGame(game.get());
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
		String view = "games/waitingPlayers";
		Game game = new Game();
		
		Player player = this.currentPlayer();
		
		game.setFirstPlayer(player);
		game.setCurrentPlayer(player);
		gameService.saveGame(game);
		
		modelMap.addAttribute("game", game);
        modelMap.addAttribute("currentPlayer", player);
        String redirect = "redirect:/games/"+game.getId().toString()+"/waitingPlayers";
		return redirect;
	}
	
	@GetMapping(path="{gameId}/waitingPlayers")
	public String joinGame(@PathVariable("gameId") Integer gameId, ModelMap modelMap) {
		String view = "games/waitingPlayers";
		
		Game game = gameService.findByGameId(gameId).get();
        Player player = this.currentPlayer();
		
		if(game.secondPlayer == null) {
            if(!this.amIFirstPlayer(game) && !this.amIThirdPlayer(game))
			    game.setSecondPlayer(player);
		}else if (!this.amISecondPlayer(game) && !this.amIFirstPlayer(game)){
			game.setThirdPlayer(player);
		}
		
		gameService.saveGame(game);
		
		modelMap.addAttribute("game", game);
		modelMap.addAttribute("currentPlayer", player);

		return view;
	}
	
	private Player currentPlayer() {
		String playerUsername = CorrentUserController.returnCurrentUserName();
		int playerId = playerService.findPlayerByUserName(playerUsername);
		Player player = playerService.findPlayerById(playerId);
		
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
