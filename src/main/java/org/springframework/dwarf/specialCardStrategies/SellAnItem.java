package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.card.StrategyName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
public class SellAnItem implements CardStrategy {
	
	@Override
	public void actions(Player player, String cardName) {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		// habria que hacer un form para que el player seleccione que recursos quiere a cambio del objeto
	}

	@Override
	public StrategyName getName() {
		return StrategyName.SELL_ITEM;
	}

}
