package org.springframework.dwarf.game;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jpatterns.gof.StatePattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.Context;

@StatePattern.ConcreteState
@org.springframework.stereotype.Component
public class ActionSelection implements GamePhase{
	
	
	 
    private GameService gameService;
    
    private WorkerService workerService;
    
    private MountainDeckService mountainDeckService;
    
    
    private BoardCellService boardCellService;
    

   @Autowired
	public ActionSelection(WorkerService ws, GameService gs, MountainDeckService mountainDeckService, BoardCellService boardCellService) {
		super();
		this.gameService = gs;
		this.workerService = ws;
		this.mountainDeckService = mountainDeckService;
		this.boardCellService = boardCellService;
	}
    
	


	@Override
	public void phaseResolution(Game game) {
		Player currentPlayer = game.getCurrentPlayer();
		Player loggedUser = LoggedUserController.loggedPlayer();
		// runs only once
		if (!currentPlayer.equals(loggedUser))
			return;

		//TODO Los jugadores tienen que colocar sus trabajadores
		
		List<Player> players = game.getPlayersList();
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
			game.setPhase(new ActionResolution(workerService, gameService, mountainDeckService, boardCellService));	
		}
		
	}
	
	private void changeCurrentPlayer(Game game) throws CreateGameWhilePlayingException {
		List<Player> turn = game.getPlayersList();
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