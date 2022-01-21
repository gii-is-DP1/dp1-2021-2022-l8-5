package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class MusterAnArmyTests {
	@Autowired
	protected MusterAnArmy mana;

	@Autowired
	private GameService gameService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private PlayerService playerService;

	private Game game;
	private Board board;
	private Player p1;

	@BeforeEach
	void setup() throws Exception {
		game = gameService.findByGameId(2).get();
		p1 = playerService.findPlayerById(2);
		board = boardService.createBoard(game);
	}

	@Test
	void testActions() {
		mana.actions(p1, "");
		assertThat(game.getMusterAnArmyEffect()).isTrue();
		board.getBoardCells().stream().forEach(cell -> {
			assertThat(cell.getIsDisabled()).isFalse();
		});
	}

	@Test
	void testGetName() {
		StrategyName name = mana.getName();
		assertThat(name).isEqualTo(StrategyName.MUSTER_AN_ARMY);
	}
}
