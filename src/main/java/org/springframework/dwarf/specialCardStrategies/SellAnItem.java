package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

@StrategyPattern.ConcreteStrategy
@Component
public class SellAnItem implements CardStrategy {
	
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private GameService gameService;
	
	@Override
	public void actions(Player player, String cardName) {
		// 1 objeto --> 5 recursos aleatorios
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Resources resources = resourcesService.findByPlayerIdAndGameId(player.getId(), game.getId()).get();
		
		try {
			resources.addResource(ResourceType.ITEMS, -1);
			this.setResourcesToPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setResourcesToPlayer() {
		
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SELL_AN_ITEM;
	}

}
