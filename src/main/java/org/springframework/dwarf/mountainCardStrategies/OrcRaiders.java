package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

@StrategyPattern.ConcreteStrategy
@Component
public class OrcRaiders implements CardStrategy{
	
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private LoggedUserController loggedUserController;
	
	@Override
	public void actions(Player player, String cardName) throws Exception {
		Player loggedUser = loggedUserController.loggedPlayer();
		Game game = gameService.findByGameId(gameService.getCurrentGameId(loggedUser)).get();
		Boolean defended = player != null || game.getMusterAnArmyEffect();
		
		if(!defended) {
			game.setCanResolveActions(false);
		
			try {
				gameService.saveGame(game);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (CreateGameWhilePlayingException e) {
				e.printStackTrace();
			}
		} else {
			if(game.getMusterAnArmyEffect())
				return;
			Resources playerDefenderResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
			playerDefenderResources.addResource(ResourceType.BADGES, 1);
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.ORC_RAIDERS;
	}
}
