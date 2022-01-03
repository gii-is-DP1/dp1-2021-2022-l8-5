package org.springframework.dwarf.mountainCardStrategies;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StrategyPattern.ConcreteStrategy
@Component
public class GetHelp implements CardStrategy{
	@Autowired
	private WorkerService workerService;
	@Autowired
	private GameService gameService;

	@Override
	public void actions(Player player) throws IllegalPositionException {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Worker extraWorker1 = new Worker(player, game, 4);
		Worker extraWorker2 = new Worker(player, game, 4);
		
		workerService.saveWorker(extraWorker1);
		workerService.saveWorker(extraWorker2);
		
		// change players turn if you are the first
	}

	@Override
	public StrategyName getName() {
		return StrategyName.GET_HELP;
	}
}
