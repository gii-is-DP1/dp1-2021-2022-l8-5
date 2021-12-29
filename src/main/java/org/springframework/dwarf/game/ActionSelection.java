package org.springframework.dwarf.game;

import java.util.List;

import org.jpatterns.gof.StatePattern;
import org.jpatterns.gof.CompositePattern.Component;
import org.springframework.dwarf.game.GameState.GamePhase;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;

@StatePattern.ConcreteState
@org.springframework.stereotype.Component
public class ActionSelection implements GamePhase{
	
    private GameService gameService;
    
    private WorkerService workerService;

	@Override
	public void phaseResolution(Game game) {
		Player currentPlayer = game.getCurrentPlayer();
		Player loggedUser = LoggedUserController.loggedPlayer();
		// runs only once
		if (!currentPlayer.equals(loggedUser))
			return;

		//TODO Los jugadores tienen que colocar sus trabajadores
		
		List<Player> players = game.getTurnList();
		List<Worker> workersNotPlaced = workerService.findNotPlacedByPlayerIdAndGameId(currentPlayer.getId(), game.getId());
	
		while(!workersNotPlaced.get(0).getStatus()) {
			//wait
		}

		try {
			changeCurrentPlayer(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*
		for(int i=0; i<players.size(); i++) {
			List<Worker> workers = new ArrayList<Worker>(workerService.findByPlayerIdAndGameId(game.getId(), players.get(i).getId()));
			
			for(int j=0; j<workers.size(); j++) {
				Worker w = workers.get(j);		//Worker j del player i;
				
			}
		}*/
		Integer remainigWorkers = workerService.findNotPlacedAndGameId(game.getId()).size();
		if (remainigWorkers.equals(0)) {
			game.setPhase(new ActionResolution());	
		}
		
	}
	
	private void changeCurrentPlayer(Game game) throws CreateGameWhilePlayingException {
		List<Player> turn = game.getTurnList();
		Player currentPlayer = game.getCurrentPlayer();

		Integer index = turn.indexOf(currentPlayer);
		currentPlayer = turn.get((index+1)%3);
		
		game.setCurrentPlayer(currentPlayer);
		gameService.saveGame(game);
		phaseResolution(game);
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_SELECTION;
	}
}