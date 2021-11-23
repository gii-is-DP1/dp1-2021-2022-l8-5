package org.springframework.dwarf.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameValidator implements Validator {
	
	@Autowired
	private GameService gameService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Game game = (Game) target;
		List<Game> notFinishedGames = gameService.findGamesToJoin();
		
		// can't create/join a game if you already are in another unfinished game
		for(Game g : notFinishedGames) {
			if(isPlayerInGame(g, game.getFirstPlayer())) {
				errors.rejectValue("firstPlayer", "playing_another_game", "The player is already in another unfinished game");
				
			}else if(isPlayerInGame(g, game.getSecondPlayer())) {
				errors.rejectValue("secondPlayer", "playing_another_game", "The player is already in another unfinished game");
				
			}else if(isPlayerInGame(g, game.getThirdPlayer())) {
				errors.rejectValue("thirdPlayer", "playing_another_game", "The player is already in another unfinished game");
			}
		}
	}
	
	private Boolean isPlayerInGame(Game game, Player player) {
		return game.getFirstPlayer().equals(player) ||
				game.getSecondPlayer().equals(player) ||
				game.getThirdPlayer().equals(player);
	}

}
