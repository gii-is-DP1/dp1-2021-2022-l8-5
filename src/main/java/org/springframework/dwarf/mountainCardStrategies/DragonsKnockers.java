package org.springframework.dwarf.mountainCardStrategies;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class DragonsKnockers implements CardStrategy{
	
	private ResourceType resourceType;
	private Integer amount;
	
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesService resourcesService;
	
	@Override
	public void actions(Player player, String cardName) {
		//log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		this.setResources(cardName);
		
		Game game = gameService.findByGameId(gameService.getCurrentGameId(player)).get();
		
		for(Player p: game.getPlayersList()) {
			this.removeResources(p, game);
		}
		
		Resources playerDefenderResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		try {
			playerDefenderResources.setResource(ResourceType.BADGE, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setResources(String cardName) {
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
