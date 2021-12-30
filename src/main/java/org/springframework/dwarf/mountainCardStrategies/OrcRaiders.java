package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class OrcRaiders implements CardStrategy{
	
	private GameService gameService;
	
	@Override
	public void actions(Player player) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		Game game = gameService.findByGameId(gameService.getCurrentGameId(player)).get();
		
		game.setCanResolveActions(false);
		
		try {
			gameService.saveGame(game);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateGameWhilePlayingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.ORC_RAIDERS;
	}
}
