package org.springframework.dwarf.card;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.worker.IllegalPositionException;

@StrategyPattern.Strategy
public interface CardStrategy {
	void actions(Player player) throws IllegalPositionException, DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException;
	
	StrategyName getName();

}
