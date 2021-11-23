package org.springframework.dwarf.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoardServiceTest {
		
	
	@Autowired
	private BoardService boardservice;
	@Autowired
	private GameService gameservice;
	@Autowired
	private MountainDeckService mountaindesckservice;
	
	@Test
	@DisplayName("")
	void testBoardCount() {
		int count = boardservice.boardCount();
		assertThat(count).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Returns all boards")
	void testFindAllBoard() {
		Iterable<Board> iterator = boardservice.findAll();
		assertThat(iterator.spliterator().getExactSizeIfKnown()).isEqualTo(2); 
	}
	
	@Test 
	@DisplayName("Returns a board by its Id correctly")
	void testFinByBoardId() {
		int boardid=1;
		Optional<Board> board= boardservice.findByBoardId(boardid);
		Board b = board.orElse(null);
		assertThat(b.getBackground()).isEqualTo("resources/images/oro_erebor.jpg");
	}
	@Test
	@DisplayName("Delete a board")
	void testDeleteBoard() {
		int boardid=2;
		Optional<Board> board= boardservice.findByBoardId(boardid);
		if(board.isPresent()) {
			boardservice.delete(board.get());
			Optional<Board> boarddeleted =boardservice.findByBoardId(boardid);
			assertThat(boarddeleted.isPresent()).isFalse();
		}else {
			System.out.println("Board not found");
		}	
	}
	
	@Test
	@DisplayName("Save a board")
	void testSaveBoard() {
		Board board= new Board();
		board.setMountainDeck(mountaindesckservice.findByMountainDeckId(1).get());
		board.setGame(gameservice.findByGameId(2).get());
		
		boardservice.saveBoard(board);
		int boardid= board.getId();
		Optional<Board> boardtest =boardservice.findByBoardId(boardid);
		
		assertThat(boardtest.isPresent()).isTrue();
		
	}
}
