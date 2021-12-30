package org.springframework.dwarf.game;

import java.util.List;
import java.util.Random;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StatePattern.ConcreteState
@Component
public class MineralExtraction implements GamePhase{
	
	 
    private GameService gameService;
    
    private WorkerService workerService;
    
    private MountainDeckService mountainDeckService;
    
    private BoardCellService boardCellService;
    
    private BoardService boardService;
    
    @Autowired
   	public MineralExtraction(WorkerService workerService, GameService gameService, MountainDeckService mountainDeckService, BoardCellService boardCellService, BoardService boardService) {
   		super();
   		this.gameService = gameService;
   		this.workerService = workerService;
   		this.mountainDeckService = mountainDeckService;
   		this.boardCellService = boardCellService;
   		this.boardService = boardService;
   		
   	}

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
    	
    	boardService.saveBoard(board);
    	game.setPhase(new ActionSelection(workerService, gameService, mountainDeckService, boardCellService, boardService));
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