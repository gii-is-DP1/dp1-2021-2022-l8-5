package org.springframework.dwarf.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCard.MountainDeckService;
import org.springframework.dwarf.specialCard.SpecialDeck;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
public class BoardServiceTest {

	@Autowired
	private BoardService boardService;
	@Autowired
	private GameService gameService;
	@Autowired
	private MountainDeckService mountainDesckService;

	@Test
	@DisplayName("Board count")
	void testBoardCount() {
		int count = boardService.boardCount();
		assertThat(count).isEqualTo(2);
	}

	@Test
	@DisplayName("Returns all boards")
	void testFindAllBoard() {
		Iterable<Board> iterator = boardService.findAll();
		assertThat(iterator.spliterator().getExactSizeIfKnown()).isEqualTo(2);
	}

	@Test
	@DisplayName("Returns a board by its Id correctly")
	void testFinByBoardId() {
		int boardid = 1;
		Optional<Board> board = boardService.findByBoardId(boardid);
		Board b = board.orElse(null);
		assertThat(b.getBackground()).isEqualTo("resources/images/oro_erebor.jpg");
	}

	@Test
	@DisplayName("Delete a board")
	void testDeleteBoard() {
		int boardid = 2;
		Optional<Board> board = boardService.findByBoardId(boardid);
		if (board.isPresent()) {
			boardService.delete(board.get());
			Optional<Board> boarddeleted = boardService.findByBoardId(boardid);
			assertThat(boarddeleted.isPresent()).isFalse();
		} else {
			System.out.println("Board not found");
		}
	}

	@Test
	@DisplayName("Save a board")
	void testSaveBoard() {
		Board board = new Board();
		board.setMountainDeck(mountainDesckService.findByMountainDeckId(1).get());
		board.setGame(gameService.findByGameId(2).get());

		boardService.saveBoard(board);
		int boardid = board.getId();
		Optional<Board> boardtest = boardService.findByBoardId(boardid);

		assertThat(boardtest.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Create a board")
	void testCreateBoard() {
		Long boardsFound = this.boardService.findAll().spliterator().getExactSizeIfKnown();
		Game game = this.gameService.findByGameId(2).get();

		this.boardService.createBoard(game);

		Long boardsFoundAfterCreate = this.boardService.findAll().spliterator().getExactSizeIfKnown();

		assertThat(boardsFound + 1).isEqualTo(boardsFoundAfterCreate);
	}

	@Test
	@DisplayName("Find the Special Decks by the id of the board")
	void testFindSpecialDeckByBoardId() {
		Game game = gameService.findByGameId(2).get();
		boardService.createBoard(game);

		Integer boardId = this.gameService.findBoardByGameId(game.getId()).get().getId();
		List<SpecialDeck> specialDecks = this.boardService.findSpecialDeckByBoardId(boardId);

		assertThat(specialDecks.size()).isEqualTo(3); // createBoard crea 3 specialDecks
	}

}
