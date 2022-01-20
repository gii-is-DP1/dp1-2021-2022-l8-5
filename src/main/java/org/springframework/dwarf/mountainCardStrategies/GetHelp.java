package org.springframework.dwarf.mountainCardStrategies;

import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StrategyPattern.ConcreteStrategy
@Component
public class GetHelp implements CardStrategy{
	@Autowired
	private WorkerService workerService;
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	
	private static final int MAX_WORKERS = 9;

	@Override
	public void actions(Player player, String cardName) {
		
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		
		Integer totalWorkers = workerService.findPlacedByGameId(game.getId()).size()
				+ workerService.findNotPlacedByGameId(game.getId()).size();
		
		//check the amount of workers not to give more workers than tiles
		
		if (totalWorkers < MAX_WORKERS-1) {
			Worker extraWorker1 = new Worker(player, game, 4);
			Worker extraWorker2 = new Worker(player, game, 4);
			
			try {
				workerService.saveWorker(extraWorker1);
				workerService.saveWorker(extraWorker2);
			} catch (DataAccessException | IllegalPositionException e) {
				e.printStackTrace();
			}
			
		} else if (totalWorkers == MAX_WORKERS-1) {
			Worker extraWorker1 = new Worker(player, game, 4);
			
			try {
				workerService.saveWorker(extraWorker1);
			} catch (DataAccessException | IllegalPositionException e) {
				e.printStackTrace();
			}
		}
		
		if(player.getTurn().equals(1))
			changePlayerNext(game);
		
		
	}
	

	protected void changePlayerNext(Game game) {
		List<Player> turn = game.getTurnList();
		for(Player p:turn) {
			// turns start with 1
			p.setTurn((p.getTurn()%turn.size())+1);
			try {
				playerService.savePlayer(p);
			} catch (DataAccessException | DuplicatedUsernameException | DuplicatedEmailException
					| InvalidEmailException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public StrategyName getName() {
		return StrategyName.GET_HELP;
	}
}
