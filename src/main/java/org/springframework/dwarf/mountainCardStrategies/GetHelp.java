package org.springframework.dwarf.mountainCardStrategies;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.web.LoggedUserController;
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
	@Autowired
	private PlayerService playerService;

	@Override
	public void actions(Player player) throws IllegalPositionException, DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {
		log.debug(player.getUsername() + ", con id" + player.getId() + ", ha realizado la accion " + this.getName().toString());
		
		Game game = gameService.findPlayerUnfinishedGames(player).get();
		Worker extraWorker1 = new Worker(player, game, 4);
		Worker extraWorker2 = new Worker(player, game, 4);
		
		workerService.saveWorker(extraWorker1);
		workerService.saveWorker(extraWorker2);
		
		// change players turn if you are the first
		Player currentPlayer = game.getCurrentPlayer();
		Game playerGame = gameService.findPlayerGame(player);
		if(player.equals(currentPlayer) && player.getTurn().equals(game.getTurnList().get(0).getTurn())) {
			changePlayerNext(playerGame);
		}
		
	}

	private void changePlayerNext(Game game) throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException, InvalidEmailException {
		List<Player> turn = game.getTurnList();
		//Player currentPlayer = game.getCurrentPlayer();
		for(Player p:turn) {
			p.setTurn((p.getTurn()+1)%3);
			playerService.savePlayer(p);
		}
		
		
	}
	@Override
	public StrategyName getName() {
		return StrategyName.GET_HELP;
	}
}
