package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class DragonsKnockers implements CardStrategy{
	
	private ResourceType resourceType;
	private Integer amount;
	
	private GameService gameService;
	private ResourcesService resourcesService;
	
	public DragonsKnockers(String cardName) {
		if(cardName.equals("Great Dragon")) {
			this.resourceType = ResourceType.GOLD;
			this.amount = null; // null means all amount the player has
		}else if(cardName.equals("Dragon")) {
			this.resourceType = ResourceType.GOLD;
			this.amount = -1;
		}else if(cardName.equals("Knockers")) {
			this.resourceType = ResourceType.IRON;
			this.amount = -1;
		}
	}
	
	@Override
	public void actions(Player player) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game game = gameService.findByGameId(gameService.getCurrentGameId(player)).get();
		
		for(Player p: game.getPlayersList()) {
			this.removeResources(p, game);
		}
		
	}
	
	private void removeResources(Player player, Game game) {
		Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		if(this.amount == null)
			this.amount = playerResources.getGold()*-1;
		
		try {
			playerResources.setResource(resourceType, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.DRAGONS_KNOCKERS;
	}
}
