package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;

@StrategyPattern.ConcreteStrategy
@Component
public class PastGlories implements CardStrategy {
	@Override
	public void actions(Player player, String cardName) {
		
	}

	@Override
	public StrategyName getName() {
		return StrategyName.PAST_GLORIES;
	}
}
