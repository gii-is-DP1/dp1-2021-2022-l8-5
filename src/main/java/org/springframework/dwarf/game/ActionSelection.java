package org.springframework.dwarf.game;

import java.time.LocalTime;
import java.util.List;

import org.jpatterns.gof.StatePattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StatePattern.ConcreteState
@Component
public class ActionSelection implements GamePhase{
	
	@Autowired
    private GameService gameService;
	@Autowired
    private WorkerService workerService;
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LoggedUserController loggedUserController;
	
	private static final LocalTime INACTIVITY_TIMER = LocalTime.of(0, 2);
    
	@Override
	public void phaseResolution(Game game) {
		Player currentPlayer = game.getCurrentPlayer();
		Player loggedUser = loggedUserController.loggedPlayer();
		// runs only once
		if (!currentPlayer.equals(loggedUser))
			return;
		
		List<Worker> notPlacedWorkers = workerService.findNotPlacedByGameId(game.getId());
		
		
		// last worker(s) to be placed, covers only 2 workers special actions scenario
		if(notPlacedWorkers.size()==0)
			game.setPhase(GamePhaseEnum.ACTION_RESOLUTION);
		
		try {
			changeCurrentPlayer(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected void changeCurrentPlayer(Game game) throws CreateGameWhilePlayingException {
		List<Player> turn = game.getTurnList();
		Player currentPlayer = game.getCurrentPlayer();
		Boolean changePlayer = true;
		
		List<Worker> notPlacedWorkers = workerService.findNotPlacedByGameId(game.getId());
		if (notPlacedWorkers.size()==0) {
			changePlayer = false;
		}
		
		while(changePlayer) {
			Integer index = turn.indexOf(currentPlayer);
			currentPlayer = turn.get((index+1)%turn.size());
			game.setCurrentPlayer(currentPlayer);
			List<Worker> workersNotPlaced = workerService.findNotPlacedByPlayerIdAndGameId(currentPlayer.getId(), game.getId());
			changePlayer = workersNotPlaced.isEmpty();
		}
		
		Board board = gameService.findBoardByGameId(game.getId()).get();
		board.setInactivityTimer(INACTIVITY_TIMER);
		
		boardService.saveBoard(board);
		gameService.saveGame(game);
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_SELECTION;
	}
}