package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class TurnBackTests {

	@Autowired
	protected TurnBack tb;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private MountainCardService mountainCardService;

	private Player p1;
	private Game game;
	private Board board;
	private BoardCell boardCell;
	private Worker workerP1;

	@BeforeEach
	void setup() {
		game = gameService.findByGameId(2).get();
		p1 = playerService.findPlayerById(2);
		workerP1 = new Worker(p1, game, 1);
		workerP1.setXposition(1);
		workerP1.setYposition(1);
		board = boardService.createBoard(game);
		boardCell = board.getBoardCell(1, 0);

		List<MountainCard> cards = boardCell.getMountaincards();
		cards.add(mountainCardService.findByMountainCardId(10).orElse(null));
		boardCell.setMountaincards(cards);

		BoardCell boardCellOccupied = board.getBoardCell(1, 1);
		boardCellOccupied.setOccupiedBy(p1);
	}

	@Test
	void testSearchBoardCell() {
		BoardCell boardCellSearched = tb.searchBoardCell(board);
		assertThat(boardCellSearched.getMountaincards().size()).isGreaterThan(1);
		assertThat(boardCellSearched.getXposition()).isEqualTo(boardCell.getXposition());

		boardCell = board.getBoardCell(1, 0);
		List<MountainCard> cards = boardCell.getMountaincards();
		cards.remove(0);
		boardCell.setMountaincards(cards);

		boardCellSearched = tb.searchBoardCell(board);
		assertThat(boardCellSearched).isNull();
	}

	@Test
	void testRemoveTopCard() {
		BoardCell cellToRemoveTopCard = tb.searchBoardCell(board);
		Integer cardsSize = cellToRemoveTopCard.getMountaincards().size();

		tb.removeTopCard(cellToRemoveTopCard, board);
		assertThat(cellToRemoveTopCard.getMountaincards().size()).isLessThan(cardsSize);
	}

	@Test
	void testPlaceWorker() {
		tb.placeWorker(p1, boardCell);

		assertThat(boardCell.isCellOccupied()).isFalse();

		workerP1.setXposition(0);
		try {
			workerService.saveWorker(workerP1);
		} catch (DataAccessException | IllegalPositionException e) {
			e.printStackTrace();
		}
		tb.placeWorker(p1, boardCell);

		assertThat(boardCell.isCellOccupied()).isTrue();
		assertThat(boardCell.getOccupiedBy()).isEqualTo(p1);
	}

	@Test
	void testGetName() {
		StrategyName name = tb.getName();
		assertThat(name).isEqualTo(StrategyName.TURN_BACK);
	}

	@Test
	@WithMockUser(username = "test")
	void testActions() throws Exception {

		tb.actions(p1, "");

		Integer workersNotPlaced = workerService.findNotPlacedByPlayerIdAndGameId(p1.getId(), game.getId()).size();

		assertThat(workersNotPlaced).isEqualTo(0);
	}
}
