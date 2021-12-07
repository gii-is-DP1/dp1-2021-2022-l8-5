package org.springframework.dwarf.strategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.player.Player;

@StrategyPattern
public interface CardStrategy {
	
	void actions(Player player);
	
	StrategyName getName();

}
