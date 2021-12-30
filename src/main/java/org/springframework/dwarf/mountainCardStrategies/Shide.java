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
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class Shide implements CardStrategy{
	
	private GameService gameService;
	private ResourcesService resourcesService;
	
	@Autowired
	public Shide(GameService gameService) {
		this.gameService = gameService;
	}
	
	@Override
	public void actions(Player player) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game game = gameService.findByGameId(gameService.getCurrentGameId(player)).get();
		
		for(Player p: game.getPlayersList()) {
			this.exchangeResources(p, game);
		}
	}
	
	private void exchangeResources(Player player, Game game) {
		Resources playerResources = resourcesService.findByPlayerIdAndGameId(player.getId(),game.getId()).get();
		
		try {
			playerResources.setResource(ResourceType.GOLD, -2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			playerResources.setResource(ResourceType.IRON, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SHIDE;
	}
}
