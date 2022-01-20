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
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
public class MusterAnArmyTests {
	@Autowired
	protected MusterAnArmy mana;
	   
	@Autowired
	private GameService gameService;
	   
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LoggedUserController loggedUserController;
	   
	private Game game;
	   
	private Board board;
   
	@BeforeEach
	void setup() throws Exception {
		
		game = gameService.findByGameId(2).get();
		       
		boardService.createBoard(game);
		       
		board = gameService.findBoardByGameId(game.getId()).get();
		       
		List<BoardCell> boardCells = board.getBoardCells();
		boardCells.get(0).getMountaincards().get(0).setCardType(CardType.AID);
		      
		boardService.saveBoard(board);
	}

	@Test
	void testGetName() {
		StrategyName name = mana.getName();
		assertThat(name).isEqualTo(StrategyName.MUSTER_AN_ARMY);
	}
}
