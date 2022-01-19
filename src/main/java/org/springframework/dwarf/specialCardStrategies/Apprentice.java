package org.springframework.dwarf.specialCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.GameService;

@StrategyPattern.ConcreteStrategy
@Component
public class Apprentice implements CardStrategy{
	
	@Autowired
	private GameService gameService;
	@Autowired
	private BoardService boardService;
	
	@Override
	public void actions(Player player, String cardName) {
		
	}

	@Override
	public StrategyName getName() {
		return StrategyName.APPRENTICE;
	}
}
