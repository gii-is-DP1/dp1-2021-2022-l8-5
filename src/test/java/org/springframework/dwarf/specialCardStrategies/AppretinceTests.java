package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class AppretinceTests {

	@Autowired
	protected Apprentice ap;

	@Autowired
	private GameService gameService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private PlayerService playerService;

	private Board board;
	private Optional<Game> g;
	private Player p1;
	private Player p2;
	private Player p3;

	@BeforeEach
	void setup() throws Exception {
		g = gameService.findByGameId(2);
		board = boardService.createBoard(g.get());

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		p3 = playerService.findPlayerById(2);

	}

	@Test
	void testSearchBoardCell() throws Exception {
		BoardCell boardCell = board.getBoardCell(1, 0);
		boardCell.setOccupiedBy(p1);
		BoardCell boardCellindex = ap.searchBoardCell(board);
		assertThat(boardCellindex).isEqualTo(board.getBoardCell(1, 0));
	}

	@Test
	void testSearchBoardCellNegative() throws Exception {
		BoardCell boardCellindex = ap.searchBoardCell(board);
		assertThat(boardCellindex).isNull();
	}

	@Test
	void testPlaceWorker() throws Exception {
		BoardCell boardCell = board.getBoardCell(2, 2);
		boardCell.setOccupiedBy(p1);
		workerService.createPlayerWorkers(p1, g.get(), null);
		workerService.createPlayerWorkers(p2, g.get(), null);
		List<Worker> listaWorkersP2 = workerService.findByPlayerId(p2.getId()).stream().collect(Collectors.toList());
		listaWorkersP2.get(0).setXposition(0);
		listaWorkersP2.get(0).setYposition(1);

		List<Worker> listaWorkersP1 = workerService.findByPlayerId(p1.getId()).stream().collect(Collectors.toList());
		listaWorkersP1.get(0).setXposition(2);
		listaWorkersP1.get(0).setYposition(2);
		ap.placeWorker(p2, boardCell);
		Worker workerP2 = workerService.findByPlayerId(p2.getId()).stream().filter(x -> x.getXposition() != null)
				.findFirst().get();

		assertThat(boardCell.getXposition()).isEqualTo(workerP2.getXposition());
		assertThat(boardCell.getYposition()).isEqualTo(workerP2.getYposition());
	}

	@Test
	void testPlaceWorkerNegative() throws Exception {
		BoardCell boardCell = board.getBoardCell(2, 2);
		boardCell.setOccupiedBy(p1);
		workerService.createPlayerWorkers(p1, g.get(), null);

		List<Worker> listaWorkersP1 = workerService.findByPlayerId(p1.getId()).stream().collect(Collectors.toList());
		listaWorkersP1.get(0).setXposition(2);
		listaWorkersP1.get(0).setYposition(2);
		ap.placeWorker(p2, boardCell);
		Collection<Worker> workerP2 = workerService.findByPlayerId(p2.getId());

		assertThat(workerP2.size()).isEqualTo(0);

	}

	@Test
	void testGetName() {
		StrategyName name = ap.getName();
		assertThat(name).isEqualTo(StrategyName.APPRENTICE);

	}
}
