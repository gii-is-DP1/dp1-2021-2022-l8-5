package org.springframework.dwarf.game;

import java.util.List;
import java.util.Random;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.mountainCard.MountainDeckService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;

@StatePattern.ConcreteState
@Component
public class MineralExtraction implements GamePhase{
	
	@Autowired
    private GameService gameService;
	@Autowired
    private MountainDeckService mountainDeckService;
	@Autowired
    private BoardCellService boardCellService;
	@Autowired
    private BoardService boardService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private LoggedUserController loggedUserController;

	@Override
    public void phaseResolution(Game game) {
    	
		// runs only once
		if(game.getFirstPlayer()!=loggedUserController.loggedPlayer())
			return;
		
		this.removeWorkers(game);
		this.deleteAidWorkers(game);
		game.setCurrentPlayer(game.getTurnList().get(0));
		
		// picks two random cards
		MountainDeck mountaindeck = gameService.searchDeckByGameId(game.getId()).get();
        List<MountainCard> mountaincards = mountaindeck.getMountainCards();
        Random random = new Random();
        
        int index1 = random.nextInt(mountaincards.size());
        MountainCard mountaincard1 = mountaincards.remove(index1);
        
        int index2 = random.nextInt(mountaincards.size());
        MountainCard mountaincard2 = mountaincards.remove(index2);
        
        mountaindeck.setMountainCards(mountaincards);
        mountainDeckService.saveMountainDeck(mountaindeck);
        
        // set cards
    	Board board = gameService.findBoardByGameId(game.getId()).get();
    	
    	List<BoardCell> boardcells = board.getBoardCells();
    	
    	for (BoardCell b : boardcells) {
    		this.setCard(mountaincard1, b);
    		this.setCard(mountaincard2, b);
    	}
    	
    	boardService.saveBoard(board);
    	game.setPhase(GamePhaseEnum.ACTION_SELECTION);
    }
	
	protected void removeWorkers(Game game) {
		List<Worker> workersPlaced = workerService.findPlacedByGameId(game.getId());
		Board board = gameService.findBoardByGameId(game.getId()).get();
		for(Worker worker: workersPlaced) {
			if (worker.getXposition() != 0) {
				this.setAndSaveBoardCell(board.getBoardCell(worker.getXposition(), worker.getYposition()));
			}
			this.setAndSaveWorker(worker);
		}
	}
	
	protected void deleteAidWorkers(Game game) {
		for (Player player : game.getPlayersList()) {
			workerService.deletePlayerAidWorkers(player);
		}
		
	}
	
	protected void setAndSaveWorker(Worker worker) {
		worker.setXposition(null);
		worker.setYposition(null);
		worker.setStatus(false);
		
		try {
			workerService.saveWorker(worker);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	protected void setAndSaveBoardCell(BoardCell boardCell) {
		boardCell.setOccupiedBy(null);
		boardCell.setIsDisabled(false);
		boardCellService.saveBoardCell(boardCell);
	}
    
    protected void setCard(MountainCard mountaincard, BoardCell boardcell) {
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