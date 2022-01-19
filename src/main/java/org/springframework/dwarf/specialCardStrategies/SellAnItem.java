package org.springframework.dwarf.specialCardStrategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Resources resources = resourcesService.findByPlayerIdAndGameId(player.getId(), game.getId()).get();
		
		try {
			resources.addResource(ResourceType.ITEMS, -1);
			this.setResourcesToPlayer(resources);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void setResourcesToPlayer(Resources resources) {
		Map<ResourceType, Integer> resourcesRecieved = this.giveRandomResources();
		
		for(ResourceType type: resourcesRecieved.keySet()) {
			try {
				resources.addResource(type, resourcesRecieved.get(type));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected Map<ResourceType, Integer> giveRandomResources() {
		Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
		resources.put(ResourceType.IRON, 0);
		resources.put(ResourceType.GOLD, 0);
		resources.put(ResourceType.STEEL, 0);
		
		Integer totalAmount = 5;
		for(int i=0; i<totalAmount; i++) {
			List<ResourceType> resourcesTypes = resources.keySet().stream().collect(Collectors.toList());
			int indexResource = (int) Math.floor(Math.random()*(resourcesTypes.size()-1));
			Integer currentAmount = resources.get(resourcesTypes.get(indexResource));
			resources.put(resourcesTypes.get(indexResource), currentAmount+1);
		}
		
		return resources;
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SELL_AN_ITEM;
	}

}
