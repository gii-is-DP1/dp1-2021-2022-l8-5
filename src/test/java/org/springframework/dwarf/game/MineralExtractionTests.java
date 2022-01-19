package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellRepository;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCardStrategies.Shide;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class MineralExtractionTests {

	@Autowired
	protected MineralExtraction me;

	@Autowired
	protected BoardCellService bcs;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private BoardService bs;

	private Player p1;

	@BeforeEach
	void setup() throws Exception {

		p1 = playerService.findPlayerById(4);

	}

	@Test
	void testGetName() {
		GamePhaseEnum name = me.getPhaseName();
		assertThat(name).isEqualTo(GamePhaseEnum.MINERAL_EXTRACTION);

	}

	@Test
	void testSetAndSaveBoardCell() {
		BoardCell bc = bcs.createBoardCell(1, 0);
		bc.setOccupiedBy(p1);
		assertThat(bc.getOccupiedBy()).isNotNull();
		me.setAndSaveBoardCell(bc);
		bc = bcs.findByBoardCellId(1).get();
		assertThat(bc.getOccupiedBy()).isNull();

	}

	@Test
	void testSetAndSaveWorker() {
		Worker w = workerService.findByWorkerId(2).get();
		Integer xpos = w.getXposition();
		Integer ypos = w.getYposition();
		me.setAndSaveWorker(w);
		Worker w2 = workerService.findByWorkerId(2).get();
		Integer xpos2 = w2.getXposition();
		Integer ypos2 = w2.getYposition();
		
		assertThat(xpos).isNotEqualTo(xpos2);
		assertThat(ypos).isNotEqualTo(ypos2);
		assertThat(xpos2).isNull();
		assertThat(ypos2).isNull();
		
	}
/*
	@Test
	void testSetCard() {
		BoardCell bc = bcs.createBoardCell(1, 0);
		MountainCard mc = new MountainCard();
		mc.setXPosition(bc.getXposition());
		mc.setYPosition(bc.getYposition());
		
		mc.setName("je suis un espion");
		
		me.setCard(mc, bc);
		
		BoardCell bc2 = bcs.findByBoardCellId(bc.getId()).get();
		
		List<MountainCard> cellcards = bc2.getMountaincards();;
		
		MountainCard mc2 = cellcards.get(0);
		
		assertThat(mc2.getXPosition()).isEqualTo(mc.getXPosition());
		
		//assertThat(bc2.getMountaincards().get(0).getName()).isEqualTo("je suis un espion");
		
	}*/

}