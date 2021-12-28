package org.springframework.dwarf.game;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.jpatterns.gof.CompositePattern.Component;
import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.DwarfApplication;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StatePattern
@Component
public class GameState {
	
    @StatePattern.State
    interface GamePhase {
        void phaseResolution(Game game);
        GamePhaseEnum getPhaseName();
    }
    
    @StatePattern.ConcreteState
    @Component
    static class MineralExtraction implements GamePhase{
        
        @Autowired
        private GameService gameService;
        
        @Autowired
        private MountainDeckService mountainDeckService;
        
        @Autowired
        private BoardCellService boardCellService;

        @Override
        public void phaseResolution(Game game) {
        	
			// runs only once
			if(game.getFirstPlayer()!=LoggedUserController.loggedPlayer())
				return;
        	// picks two randoms cards
            MountainDeck mountaindeck = gameService.searchDeckByGameId(game.getId()).get();
            List<MountainCard> mountaincards = mountaindeck.getMountainCards();
            Random random = new Random();
            
            int index1 = random.nextInt(mountaincards.size());
            MountainCard mountaincard1 = mountaincards.remove(index1);
            
            int index2 = random.nextInt(mountaincards.size());
            MountainCard mountaincard2 = mountaincards.remove(index2);
            
            mountainDeckService.saveMountainDeck(mountaindeck);
            
            // set cards
        	Board board = gameService.findBoardByGameId(game.getId()).get();
        	
        	List<BoardCell> boardcells = board.getBoardCells();
        	
        	for (BoardCell b : boardcells) {
        		setCard(mountaincard1, b);
        		setCard(mountaincard2, b);
        	}
        	
        	//game.setPhase(new ActionSelection());
        }
        
        private void setCard(MountainCard mountaincard, BoardCell boardcell) {
    		List<MountainCard> cellcards = boardcell.getMountaincards();
    		if (mountaincard.getXPosition().equals(boardcell.getXposition()) &&
    				mountaincard.getYPosition().equals(boardcell.getYposition())) {
    			cellcards.add(0, mountaincard);
    			boardcell.setMountaincards(cellcards);
    			boardCellService.saveBoardCell(boardcell);
    		}
        }

		@Override
		public GamePhaseEnum getPhaseName() {
			return GamePhaseEnum.MINERAL_EXTRACTION;
		}
    }
    
    @StatePattern.ConcreteState
    @Component
    static class ActionSelection implements GamePhase{
    	
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
    
    @StatePattern.ConcreteState
    static class ActionResolution implements GamePhase{
        
        @Autowired
        private GameService gameService;
        
        @Autowired
        private WorkerService workerService;

		@Override
		public void phaseResolution(Game game) {
			
			Integer gameId = game.getId();
			List<Player> players = gameService.searchPlayersByGame(gameId);	//Todos los jugadores del game
			
			for(int i=0; i<players.size(); i++) {
				List<Worker> workers = workerService.findByPlayerIdAndGameId(gameId, players.get(i).getId())
													.stream().collect(Collectors.toList());
				for(int j=0; j<workers.size(); j++) {
					Worker w = workers.get(j);		//Worker j del player i;
					Integer wXPos = w.getXposition();
					Integer wYPos = w.getYposition();
					
					//Comprobaciones de posiciones y resolucion de acciones de las cartas pendiente (Strategy)
					
				}
			}
			
			game.setPhase(new MineralExtraction());
		}

		@Override
		public GamePhaseEnum getPhaseName() {
			return GamePhaseEnum.ACTION_RESOLUTION;
		}
    }

}
