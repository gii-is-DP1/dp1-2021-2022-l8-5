package org.springframework.dwarf.card;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.IllegalPositionException;

@StrategyPattern.Strategy
public interface CardStrategy {
	void actions(Player player) throws IllegalPositionException;
	
	StrategyName getName();

}
