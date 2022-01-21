package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class PastGloriesTest {

	@Autowired
	protected PastGlories pg;

	@Autowired
	private GameService gameService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private MountainCardService mountainCardService;

	private Game game;
	private Board board;
	private BoardCell boardCell;

	@BeforeEach
	void setup() {
		game = gameService.findByGameId(2).orElse(null);
		board = boardService.createBoard(game);
		boardCell = board.getBoardCell(1, 0);

		List<MountainCard> cards = boardCell.getMountaincards();
		cards.add(mountainCardService.findByMountainCardId(10).orElse(null));
		boardCell.setMountaincards(cards);
	}

	@Test
	void testSearchBoardCell() {
		BoardCell boardCellSearched = pg.searchBoardCell(board);
		assertThat(boardCellSearched.getMountaincards().size()).isGreaterThan(1);
		assertThat(boardCellSearched.getXposition()).isEqualTo(boardCell.getXposition());

		boardCell = board.getBoardCell(1, 0);
		List<MountainCard> cards = boardCell.getMountaincards();
		cards.remove(0);
		boardCell.setMountaincards(cards);

		boardCellSearched = pg.searchBoardCell(board);
		assertThat(boardCellSearched).isNull();
	}

	@Test
	void testMoveLastCardToTop() {
		MountainCard beforeTopCard = boardCell.getMountaincards().get(0);

		pg.moveLastCardToTop(boardCell);

		MountainCard afterTopCard = boardCell.getMountaincards().get(0);

		assertThat(beforeTopCard).isNotEqualTo(afterTopCard);
	}

	@Test
	void testGetName() {
		StrategyName name = pg.getName();
		assertThat(name).isEqualTo(StrategyName.PAST_GLORIES);
	}
}
