package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.IllegalPositionException;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
@Import(LoggedUserController.class)
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
	private BoardService boardService;

	@Autowired
	private MountainCardService mountainCardService;

	@Autowired
	private GameService gameService;

	private Player p1;

	private Player p2;

	private Player p3;

	private Game g;

	@BeforeEach
	void setup() throws Exception {

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(2);
		p3 = playerService.findPlayerById(5);
		g = new Game();
		g.setId(1);
		g.setCurrentPlayer(p1);
		g.setFirstPlayer(p1);
		g.setSecondPlayer(p2);
		g.setThirdPlayer(p3);

		p1.setTurn(1);
		p2.setTurn(2);
		p3.setTurn(3);

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
		bc = bcs.findByBoardCellId(bc.getId()).get();
		assertThat(bc.getOccupiedBy()).isNull();

	}

	@Test
	void testSetAndSaveWorker() {
		Worker w = workerService.findByWorkerId(2).get();
		w.setXposition(1);
		w.setYposition(0);
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

	@Test
	void testSetCard() {

		BoardCell bc = bcs.createBoardCell(1, 0);
		MountainCard mc = mountainCardService.findInitialCardByPosition(1, 0);

		mc.setName("je suis un espion");

		me.setCard(mc, bc);

		BoardCell bc2 = bcs.findByBoardCellId(bc.getId()).get();

		List<MountainCard> cellcards = bc2.getMountaincards();

		assertThat(cellcards.size()).isEqualTo(2);

		assertThat(bc2.getMountaincards().get(0).getName()).isEqualTo("je suis un espion");

	}

	@Test
	void testSetCardNegative() {

		BoardCell bc = bcs.createBoardCell(1, 0);
		MountainCard mc = mountainCardService.findInitialCardByPosition(1, 1);

		mc.setName("je suis un espion");

		me.setCard(mc, bc);

		BoardCell bc2 = bcs.findByBoardCellId(bc.getId()).get();

		List<MountainCard> cellcards = bc2.getMountaincards();

		assertThat(cellcards.size()).isEqualTo(1);

	}

	@Test
	void testSetCardNegative2() {

		BoardCell bc = bcs.createBoardCell(1, 0);
		MountainCard mc = mountainCardService.findInitialCardByPosition(2, 1);

		mc.setName("je suis un espion");

		me.setCard(mc, bc);

		BoardCell bc2 = bcs.findByBoardCellId(bc.getId()).get();

		List<MountainCard> cellcards = bc2.getMountaincards();

		assertThat(cellcards.size()).isEqualTo(1);

	}

	@Test
	void testRemoveWorkers() {
		List<Worker> workers = workerService.findPlacedByGameId(1);

		workers.stream().forEach(x -> x.setXposition(0));
		workers.stream().forEach(x -> {
			try {
				workerService.saveWorker(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		workers.stream().forEach(x -> assertThat(x.getXposition()).isNotNull());

		me.removeWorkers(g);

		List<Worker> workersAfter = workerService.findPlacedByGameId(1);

		workersAfter.stream().forEach(x -> assertThat(x.getXposition()).isNull());

	}

	@Test
	void testRemoveWorkers2() throws DataAccessException, IllegalPositionException, CreateGameWhilePlayingException {
		Player p2 = playerService.findPlayerById(3);
		Game g = new Game();

		g.setFirstPlayer(p2);
		g.setCurrentPlayer(p2);

		Worker w = new Worker(p2, g, 4);

		w.setXposition(1);
		w.setYposition(0);
		w.setStatus(true);
		gameService.saveGame(g);
		workerService.saveWorker(w);

		Board b = boardService.createBoard(g);

		BoardCell bc = b.getBoardCell(1, 0);
		bc.setOccupiedBy(p1);

		assertThat(w.getXposition()).isNotNull();

		me.removeWorkers(g);

		List<Worker> workersAfter = workerService.findPlacedByGameId(1);

		workersAfter.stream().forEach(x -> assertThat(x.getXposition()).isNull());

	}

	@WithMockUser(username = "test")
	@Test
	void testPhaseResolutionNegative() {
		g.setFirstPlayer(p2);
		GamePhaseEnum ogphase = g.getCurrentPhaseName();

		me.phaseResolution(g);

		GamePhaseEnum newphase = g.getCurrentPhaseName();

		assertThat(ogphase).isEqualTo(newphase);

	}

	@WithMockUser(username = "test")
	@Test
	void testPhaseResolutionPositive() {
		g.setFirstPlayer(p1);
		List<Worker> workers = workerService.findPlacedByGameId(1);

		workers.stream().forEach(x -> x.setXposition(0));
		workers.stream().forEach(x -> {
			try {
				workerService.saveWorker(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		GamePhaseEnum ogphase = g.getCurrentPhaseName();

		me.phaseResolution(g);

		GamePhaseEnum newphase = g.getCurrentPhaseName();

		assertThat(ogphase).isNotEqualTo(newphase);
		assertThat(newphase).isEqualTo(GamePhaseEnum.ACTION_SELECTION);

	}

	@Test
	void testDeleteAidWorkers() throws DataAccessException, IllegalPositionException {

		Worker w = new Worker(p1, g, 4);

		workerService.saveWorker(w);

		List<Worker> Workers = workerService.findPlayerAidWorkers(p1.getId());

		assertThat(Workers.size()).isEqualTo(1);

		me.deleteAidWorkers(g);

		List<Worker> WorkersAfter = workerService.findPlayerAidWorkers(p1.getId());

		assertThat(WorkersAfter.size()).isEqualTo(0);

	}

}