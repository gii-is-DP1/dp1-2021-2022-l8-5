package org.springframework.dwarf.game;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.StatePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellActionsComparator;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.mountain_card.CardType;
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
		List<BoardCell> cellsToResolve = this.getCellstoResolveActions(game);
		
		for(BoardCell cell: cellsToResolve) {
			Player player = cell.getOccupiedBy();
			MountainCard mountainCard = cell.getMountaincards().get(0);
			// check orc raiders effect
			if(this.canResolveAction(game, mountainCard))
				mountainCard.cardAction(player, applicationContext);
		}
		
		game.setCanResolveActions(true);
		game.setPhase(GamePhaseEnum.MINERAL_EXTRACTION);
	}
	
	private boolean canResolveAction(Game game, MountainCard mountainCard) {
		boolean canResolve = game.getCanResolveActions();
		
		// orc raiders ONLY avoid resolve MINE cards
		if(!mountainCard.getCardType().equals(CardType.MINE) && !canResolve)
			canResolve = true;
			
		return canResolve;
	}
	
	private List<BoardCell> getCellstoResolveActions(Game game) {
		Board board = gameService.findBoardByGameId(game.getId()).get();
		
		// defend cards always resolve action
		List<BoardCell> cellsToResolve = boardCellService.findAllByBoardId(board.getId()).stream()
				.filter(cell -> cell.getOccupiedBy() != null
					|| cell.getMountaincards().get(0).getCardType().equals(CardType.DEFEND))
				.collect(Collectors.toList());
		
		Collections.sort(cellsToResolve, new BoardCellActionsComparator());
		
		return cellsToResolve;
	}

	@Override
	public GamePhaseEnum getPhaseName() {
		return GamePhaseEnum.ACTION_RESOLUTION;
	}
}