package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class CollapseTheShaftsTests {

	@Autowired
	protected CollapseTheShafts cts;

	@Autowired
	private GameService gs;
	@Autowired
	private BoardService bs;
	@Autowired
	private MountainCardService mcs;

	private Board board;

	@BeforeEach
	void setup() throws Exception {
		Optional<Game> g = gs.findByGameId(2);
		board = bs.createBoard(g.get());
		List<MountainCard> listacartas = new ArrayList<MountainCard>();
		listacartas.add(mcs.findByMountainCardId(1).get());
		listacartas.add(mcs.findByMountainCardId(10).get());
		board.getBoardCell(1, 0).setMountaincards(listacartas);

	}

	@Test
	void testGetName() {
		StrategyName name = cts.getName();
		assertThat(name).isEqualTo(StrategyName.COLLAPSE_THE_SHAFTS);
	}

	@Test
	void testFromTopToBottom() throws Exception {
		MountainCard cardBefore = board.getBoardCell(1, 0).getMountaincards().get(0);
		cts.fromTopToBottom(board);
		MountainCard cardAfter = board.getBoardCell(1, 0).getMountaincards().get(0);
		assertThat(cardBefore.getName()).isNotEqualTo(cardAfter.getName());
	}

}
