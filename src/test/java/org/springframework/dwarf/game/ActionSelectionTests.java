package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class ActionSelectionTests {

	@Autowired
	protected ActionSelection actionSelection;

	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private LoggedUserController loggedUserController;

	private Optional<Game> g;
	private Player loggedUser;
	private Player p1;
	private Player p2;
	private Player p3;
	private Board board;
	private Player currentPlayer;

	@BeforeEach
	void setup() throws Exception {
		g = gameService.findByGameId(2);
		board = boardService.createBoard(g.get());
		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		p3 = playerService.findPlayerById(2);
		loggedUser = loggedUserController.loggedPlayer();
		currentPlayer = g.get().getCurrentPlayer();
		currentPlayer = loggedUser;

		g.get().getPlayersList().stream().forEach(x -> x.setTurn(g.get().getPlayerPosition(x) + 1));

		gameService.saveGame(g.get());

	}

	@Test
	void testGetName() {
		GamePhaseEnum name = actionSelection.getPhaseName();
		assertThat(name).isEqualTo(GamePhaseEnum.ACTION_SELECTION);
	}

	@WithMockUser(username = "test")
	@Test
	void testChangeCurrentPlayer() throws Exception {
		workerService.createPlayerWorkers(p1, g.get(), null);
		workerService.createPlayerWorkers(p2, g.get(), null);
		workerService.createPlayerWorkers(p3, g.get(), null);
		String currentPlayerBefore = g.get().getCurrentPlayer().getUsername();
		actionSelection.changeCurrentPlayer(g.get());
		String currentPlayerAfter = g.get().getCurrentPlayer().getUsername();
		assertThat(currentPlayerBefore).isNotEqualTo(currentPlayerAfter);
	}

	@WithMockUser(username = "test")
	@Test
	void testChangeCurrentPlayerNegative() throws Exception {
		String currentPlayerBefore = g.get().getCurrentPlayer().getUsername();
		actionSelection.changeCurrentPlayer(g.get());
		String currentPlayerAfter = g.get().getCurrentPlayer().getUsername();
		assertThat(currentPlayerBefore).isEqualTo(currentPlayerAfter);
	}

	@WithMockUser(username = "test")
	@Test
	void testPhaseResolution() throws Exception {
		workerService.deletePlayerWorker(g.get().getFirstPlayer());
		workerService.deletePlayerWorker(g.get().getSecondPlayer());
		workerService.deletePlayerWorker(g.get().getThirdPlayer());

		actionSelection.phaseResolution(g.get());

		assertThat(g.get().getCurrentPhaseName()).isNotEqualTo(GamePhaseEnum.ACTION_SELECTION);

	}

	@WithMockUser(username = "test")
	@Test
	void testPhaseResolutionNegative() throws Exception {
		g.get().setCurrentPhaseName(GamePhaseEnum.ACTION_SELECTION);
		workerService.createPlayerWorkers(p1, g.get(), 1);
		workerService.createPlayerWorkers(p2, g.get(), null);
		workerService.createPlayerWorkers(p3, g.get(), null);
		actionSelection.phaseResolution(g.get());
		assertThat(g.get().getCurrentPhaseName()).isEqualTo(GamePhaseEnum.ACTION_SELECTION);
	}

}
