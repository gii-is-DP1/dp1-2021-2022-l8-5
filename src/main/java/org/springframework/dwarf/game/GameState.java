package org.springframework.dwarf.game;


import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;


@StatePattern
public class GameState {
	
    @StatePattern.State
    interface GamePhase {

        void phaseResolution();
        void setGame(Game game);
        
    }
    
    /*@StatePattern.Context
    static class GamePhaseControl {
        private GamePhase currentPhase;

        public GamePhaseControl() {
            currentPhase = new MineralExtraction();
        }

        public void setPhase(GamePhase phase) {
            currentPhase = phase;
        }

        @Override
        public String toString() {
            return String.format("Game Phase Control [current phase = %s]", currentPhase);
        }
    }*/
    
    @StatePattern.ConcreteState
    static class MineralExtraction implements GamePhase{
    	
        private Game game;
        
        @Autowired
        private GameService gameService;
        
        @Autowired
        private MountainDeckService mountainDeckService;
        
        @Autowired
        private BoardCellService boardCellService;

        @Override
        public void phaseResolution() {
        	
        	//Sacar el mazo de la partida
        	//Coger 2 cartas aleatorias del mazo y eliminarlas de este
        	
            MountainDeck mountaindeck = gameService.searchDeckByGameId(game.getId()).get();
            List<MountainCard> mountaincards = mountaindeck.getMountainCards();
            Random random = new Random();
            
            int index1 = random.nextInt(mountaincards.size());
            MountainCard mountaincard1 = mountaincards.remove(index1);
            
            int index2 = random.nextInt(mountaincards.size());
            MountainCard mountaincard2 = mountaincards.remove(index2);
            
            mountainDeckService.saveMountainDeck(mountaindeck);
            
            
            //Sacar las boardcells relacionadas con el board de este game, mediante board_id
            //Comprobar xposition e yposition de mountaincard1 y mountaincard2, y asignarselas a las boardcells
            //cuyas xposition e yposition coincidan
            
            
        	Board board = gameService.findBoardByGameId(game.getId()).get();
        	
        	List<BoardCell> boardcells = board.getBoardCells();
        	
        	for (BoardCell b : boardcells) {
        		setCard(mountaincard1, b);
        		setCard(mountaincard2, b);
        	}
        	
           
        }

        @Override
        public void setGame(Game game) {
            this.game = game;
            this.game.setCurrentPhaseName(GamePhaseEnum.MINERAL_EXTRACTION);
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
        
    	
    }
    
    @StatePattern.ConcreteState
    static class ActionSelection implements GamePhase{
    	
        private Game game;
        
        private GameService gameService;
        
        private WorkerService workerService;


		@Override
		public void phaseResolution() {
			//TODO Los jugadores tienen que colocar sus trabajadores
			
			Integer gameId = game.getId();
			List<Player> players = gameService.searchPlayersByGame(gameId);	//Todos los jugadores del game
			
			for(int i=0; i<players.size(); i++) {
				List<Worker> workers = workerService.findByPlayerIdAndGameId(gameId, players.get(i).getId())
													.stream().collect(Collectors.toList());
				for(int j=0; j<workers.size(); j++) {
					Worker w = workers.get(j);		//Worker j del player i;
					
				}
			}
		}
		
		

        @Override
        public void setGame(Game game) {
            this.game = game;
            this.game.setCurrentPhaseName(GamePhaseEnum.ACTION_SELECTION);
        }
    
    }
    
    @StatePattern.ConcreteState
    static class ActionResolution implements GamePhase{
    	
        private Game game;
        
        @Autowired
        private GameService gameService;
        
        @Autowired
        private WorkerService workerService;

		@Override
		public void phaseResolution() {
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
			
		}

        @Override
        public void setGame(Game game) {
            this.game = game;
            this.game.setCurrentPhaseName(GamePhaseEnum.ACTION_RESOLUTION);
        }
    	
    }

}
