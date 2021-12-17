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
        


		@Override
		public void phaseResolution() {
			//TODO Los jugadores tienen que colocar sus trabajadores
			
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
			Player p1 = gameService.searchPlayerOneByGame(game.getId());	//Player1

			List<Worker> workersP1 = workerService.findByPlayerId(p1.getId()).stream().collect(Collectors.toList()); //Workers de P1
			
			for(int i=0; i<workersP1.size(); i++) {
				Worker w = workersP1.get(i);			//Cada worker
				Integer posXw = w.getXposition();
				Integer posYw = w.getYposition();
				
				//Falta comprobación de las acciones de cada carta (Strategy)
			}
			
			Player p2 = gameService.searchPlayerTwoByGame(game.getId());	//Player2

			List<Worker> workersP2 = workerService.findByPlayerId(p2.getId()).stream().collect(Collectors.toList()); //Workers de P1
			
			for(int i=0; i<workersP2.size(); i++) {
				Worker w = workersP2.get(i);			//Cada worker
				Integer posXw = w.getXposition();
				Integer posYw = w.getYposition();
				
				//Falta comprobación de las acciones de cada carta (Strategy)
			}
			
			Player p3 = gameService.searchPlayerThreeByGame(game.getId());	//Player3

			List<Worker> workersP3 = workerService.findByPlayerId(p3.getId()).stream().collect(Collectors.toList()); //Workers de P1
			
			for(int i=0; i<workersP3.size(); i++) {
				Worker w = workersP3.get(i);			//Cada worker
				Integer posXw = w.getXposition();
				Integer posYw = w.getYposition();
				
				//Falta comprobación de las acciones de cada carta (Strategy)
			}
			
		}

        @Override
        public void setGame(Game game) {
            this.game = game;
            this.game.setCurrentPhaseName(GamePhaseEnum.ACTION_RESOLUTION);
        }
    	
    }

}
