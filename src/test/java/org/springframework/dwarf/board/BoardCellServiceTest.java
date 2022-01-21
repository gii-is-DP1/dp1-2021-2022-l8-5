package org.springframework.dwarf.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
public class BoardCellServiceTest {

	@Autowired
	private BoardCellService boardCellService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MountainCardService mountainCardService;

	@Test
	@DisplayName("Save a board cell")
	void testSaveBoardCell() {
		long cellsFound = this.boardCellService.count();
		MountainCard card = this.mountainCardService.findByMountainCardId(1).get();
		Integer xposition = 1;
		Integer yposition = 0;
		BoardCell cell = new BoardCell(xposition, yposition, List.of(card));

		this.boardCellService.saveBoardCell(cell);

		long cellsAfterSave = this.boardCellService.count();

		assertThat(cellsFound + 1).isEqualTo(cellsAfterSave);
	}

	@Test
	@DisplayName("Find all board cells")
	void testFindAllBoardCells() {
		Long boardCellsFound = this.boardCellService.findAll()
				.spliterator()
				.getExactSizeIfKnown();
		assertThat(boardCellsFound).isEqualTo(this.boardCellService.count());
	}

	@Test
	@DisplayName("Returns a board cell by its Id correctly")
	void testFindByBoardCellId() {
		MountainCard card = this.mountainCardService.findByMountainCardId(1).get();
		Integer xposition = 1;
		Integer yposition = 0;
		BoardCell cell = new BoardCell(xposition, yposition, List.of(card));

		this.boardCellService.saveBoardCell(cell);

		Optional<BoardCell> cellById = this.boardCellService.findByBoardCellId(cell.getId());

		assertThat(cellById.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Create a board cell")
	void testCreateBoardCell() {
		Long cellsFound = this.boardCellService.count();
		Integer xposition = 2;
		Integer yposition = 0;

		this.boardCellService.createBoardCell(xposition, yposition);

		Long cellsAfterCreate = this.boardCellService.count();

		assertThat(cellsFound + 1).isEqualTo(cellsAfterCreate);

	}

	@Test
	@DisplayName("Delete a board cell")
	void testDeleteBoardCell() {
		Integer xposition = 2;
		Integer yposition = 0;
		BoardCell boardCell = this.boardCellService.createBoardCell(xposition, yposition);

		Long cellsFound = this.boardCellService.count();

		this.boardCellService.delete(boardCell);

		Long cellsAfterDelete = this.boardCellService.count();

		assertThat(cellsFound - 1).isEqualTo(cellsAfterDelete);
	}

	@Test
	@DisplayName("Find By Position")
	void testFindByPosition() {
		Integer xposition = 2;
		Integer yposition = 0;

		Board b = boardService.createBoard(gameService.findByGameId(2).get());

		BoardCell bc = b.getBoardCell(xposition, yposition);

		BoardCell bc2 = boardCellService.findByPosition(xposition, yposition, b.getId());

		assertThat(bc).isEqualTo(bc2);

	}

	@Test
	@DisplayName("Find All By Board")
	void testFindAllByBoard() {

		Board b = boardService.createBoard(gameService.findByGameId(2).get());

		List<BoardCell> bcs = b.getBoardCells();

		List<BoardCell> bcs2 = boardCellService.findAllByBoardId(b.getId());

		assertThat(bcs).containsAll(bcs2);

	}

	@Test
	@DisplayName("Find Occupied By Board")
	void testFindOccupiedByBoardId() {

		Player p = playerService.findPlayerById(1);

		Board b = boardService.createBoard(gameService.findByGameId(2).get());

		List<BoardCell> bcs = b.getBoardCells();

		bcs.stream().forEach(x -> x.setOccupiedBy(p));

		bcs.stream().forEach(x -> boardCellService.saveBoardCell(x));

		List<BoardCell> bcs2 = boardCellService.findOccupiedByBoardId(b.getId());

		assertThat(bcs).containsAll(bcs2);

	}
}
