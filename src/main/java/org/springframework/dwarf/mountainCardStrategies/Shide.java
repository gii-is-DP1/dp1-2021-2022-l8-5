package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class Shide implements CardStrategy{
	GameService gameService;
	
	@Autowired
	public Shide(GameService gameService) {
		this.gameService = gameService;
	}
	
	@Override
	public void actions(Player player) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SHIDE;
	}
}
