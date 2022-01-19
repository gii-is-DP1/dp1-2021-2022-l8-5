package org.springframework.dwarf.specialCardStrategies;

import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainCard;

@StrategyPattern.ConcreteStrategy
@Component
public class PastGlories implements CardStrategy {
	
	@Autowired
	private GameService gameService;
	@Autowired
	BoardService boardService;
	@Autowired
	BoardCellService boardCellService;
	
	@Override
	public void actions(Player player, String cardName) {
		Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();
		
		BoardCell cell = this.searchBoardCell(board);
		this.moveLastCardToTop(cell);
		boardService.saveBoard(board);
	}
	
	private BoardCell searchBoardCell(Board board) {
		List<BoardCell> cells = board.getBoardCells();
		
		cells = cells.stream()
				.filter(cell -> cell.getMountaincards().size() > 1)
				.collect(Collectors.toList());
		int indexCell = (int) Math.floor(Math.random()*(cells.size()-1));
		
		return cells.get(indexCell);
	}
	
	private void moveLastCardToTop(BoardCell cell) {
		List<MountainCard> cellCards = cell.getMountaincards();
		MountainCard cardToMove = cellCards.get(cellCards.size()-1);
		cellCards.remove(cardToMove);
		cellCards.add(0, cardToMove);
		boardCellService.saveBoardCell(cell);
	}

	@Override
	public StrategyName getName() {
		return StrategyName.PAST_GLORIES;
	}
}
