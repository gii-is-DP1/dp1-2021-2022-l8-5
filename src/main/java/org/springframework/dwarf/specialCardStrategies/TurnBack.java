package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;

@StrategyPattern.ConcreteStrategy
@Component
public class TurnBack implements CardStrategy {
	
	@Override
	public void actions(Player player, String cardName) {

	//Remove the top card from 1 location and suffle it back into the mountain
	//Immediatly place 1 worker on that location

		
	}

	@Override
	public StrategyName getName() {
		return StrategyName.TURN_BACK;
	}

}
