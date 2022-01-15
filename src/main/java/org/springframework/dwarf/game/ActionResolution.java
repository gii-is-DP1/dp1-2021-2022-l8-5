package org.springframework.dwarf.game;

import java.util.List;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;

@StatePattern.ConcreteState
@Component
public class ActionResolution implements GamePhase{
    
	@Autowired
    private GameService gameService;
    @Autowired
    private BoardCellService boardCellService;
    @Autowired
    private ApplicationContext applicationContext;

	@Override
	public void phaseResolution(Game game) {
		
		// funcion service no testeada
		List<BoardCell> occupiedCells = this.getOccupiedCells(game);
		
		for(BoardCell cell: occupiedCells) {
			Player player = cell.getOccupiedBy();
			MountainCard mountainCard = cell.getMountaincards().get(0);
			mountainCard.cardAction(player, applicationContext);
			//this.removeTopCard(cell);
		}
		
		game.setPhase(GamePhaseEnum.MINERAL_EXTRACTION);
	}
	
	private List<BoardCell> getOccupiedCells(Game game) {
		Board board = gameService.findBoardByGameId(game.getId()).get();
		List<BoardCell> occupiedCells = boardCellService.findOccupiedByBoardId(board.getId());
		return occupiedCells;
	}
	
	private void removeTopCard(BoardCell cell) {
		List<MountainCard> mountainCards = cell.getMountaincards();
		mountainCards.remove(0);
		cell.setMountaincards(mountainCards);
		boardCellService.saveBoardCell(cell);
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_RESOLUTION;
	}
}