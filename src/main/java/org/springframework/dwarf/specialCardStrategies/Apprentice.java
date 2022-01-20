package org.springframework.dwarf.specialCardStrategies;

import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.card.CardStrategy;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;

@StrategyPattern.ConcreteStrategy
@Component
public class Apprentice implements CardStrategy {
	
	@Autowired
	private GameService gameService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private BoardCellService boardCellService;
	
	private static final int SPECIAL_CARDS_COLUMN = 0;
	
	@Override
	public void actions(Player player, String cardName) {
		Game currentGame = gameService.findPlayerUnfinishedGames(player).get();
		Board board = gameService.findBoardByGameId(currentGame.getId()).get();
		
		BoardCell boardCell = this.searchBoardCell(board);
		if(boardCell != null)
			this.placeWorker(player, boardCell);
	}
	
	protected BoardCell searchBoardCell(Board board) {
		List<BoardCell> cells = board.getBoardCells();
		
		cells = cells.stream()
				.filter(cell -> cell.getOccupiedBy() != null)
				.collect(Collectors.toList());
		
		int indexCell = -1;
		if(cells.size() > 0)
			indexCell = (int) Math.floor(Math.random()*(cells.size()-1));
		
		BoardCell cell = indexCell==-1 ? null:cells.get(indexCell);
		return cell;
	}
	
	protected void placeWorker(Player player, BoardCell boardCell) {
		Worker workerToPlace = workerService.findByPlayerId(player.getId()).stream()
				.filter(worker -> worker.getXposition()==SPECIAL_CARDS_COLUMN)
				.findFirst().orElse(null);
		
		if(workerToPlace == null)
			return;
		
		workerToPlace.setXposition(boardCell.getXposition());
		workerToPlace.setYposition(boardCell.getYposition());
		try {
			workerService.saveWorker(workerToPlace);
		} catch (DataAccessException | IllegalPositionException e) {
			e.printStackTrace();
		}
		
		boardCell.setOccupiedBy(player);
		boardCellService.saveBoardCell(boardCell);
	}

	@Override
	public StrategyName getName() {
		return StrategyName.APPRENTICE;
	}
}
