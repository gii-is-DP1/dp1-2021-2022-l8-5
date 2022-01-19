package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.card.CardStrategy;

@StrategyPattern.ConcreteStrategy
@Component
public class MusterAnArmy implements CardStrategy{

	@Autowired
	GameService gameService;
	
	@Override
	public void actions(Player player, String cardName) {
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		game.setMusterAnArmyEffect(true);
		try {
			gameService.saveGame(game);
		} catch (DataAccessException | CreateGameWhilePlayingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.MUSTER_AN_ARMY;
	}
}
