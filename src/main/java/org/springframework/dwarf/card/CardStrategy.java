package org.springframework.dwarf.card;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.strategies.CardStrategy.StrategyName;

@StrategyPattern.Strategy
public interface CardStrategy {
	void actions(Player player);
	
	StrategyName getName();

}
