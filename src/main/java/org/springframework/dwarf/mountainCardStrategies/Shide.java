package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class Shide implements CardStrategy{
	
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesService resourcesService;
	
	@Override
	public void actions(Player player, String cardName) {
		//log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game game = gameService.findByGameId(gameService.getCurrentGameId(player)).get();
		
		for(Player p: game.getPlayersList()) {
			this.exchangeResources(p, game);
		}
		
		Resources playerDefenderResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		try {
			playerDefenderResources.setResource(ResourceType.BADGE, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exchangeResources(Player player, Game game) {
		Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		
		try {
			playerResources.setResource(ResourceType.GOLD, -2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			playerResources.setResource(ResourceType.IRON, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SHIDE;
	}
}
