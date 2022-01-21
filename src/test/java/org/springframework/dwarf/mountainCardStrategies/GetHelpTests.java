package org.springframework.dwarf.mountainCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))

public class GetHelpTests {

	@Autowired
	private WorkerService workerService;
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;

	@Autowired
	protected GetHelp gh;

	private Game game;
	private Player p1;
	private Player p2;

	@BeforeEach
	void setup() throws Exception {

		game = gameService.findByGameId(2).get();

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		game.getPlayersList().stream().forEach(x -> x.setTurn(game.getPlayerPosition(x) + 1));

		workerService.createPlayerWorkers(p1, game, null);
		game.getTurnList();
		p1.setTurn(1);

	}

	@Test
	void testGetName() {
		StrategyName name = gh.getName();
		assertThat(name).isEqualTo(StrategyName.GET_HELP);

	}

	@Test
	void testChangePlayerNext() throws Exception {
		Game g = gameService.findByGameId(2).get();
		List<Player> initialTurns = g.getTurnList();
		gh.changePlayerNext(g);
		List<Player> newTurns = g.getTurnList();
		assertThat(initialTurns).isNotEqualTo(newTurns);

	}

	@Test
	void testActions() throws Exception {
		gh.actions(p1, "GetHelp");
		Integer workersNumber = workerService.findByPlayerId(p1.getId()).size();
		assertThat(workersNumber).isEqualTo(4);

		gh.actions(p1, "GetHelp");
		Integer workersNumber2 = workerService.findByPlayerId(p1.getId()).size();
		assertThat(workersNumber2).isEqualTo(6);

		gh.actions(p1, "GetHelp");
		Integer workersNumber3 = workerService.findByPlayerId(p1.getId()).size();
		assertThat(workersNumber3).isEqualTo(8);

		gh.actions(p1, "GetHelp");
		Integer workersNumber4 = workerService.findByPlayerId(p1.getId()).size();
		assertThat(workersNumber4).isEqualTo(9);

		gh.actions(p1, "GetHelp");
		Integer workersNumber5 = workerService.findByPlayerId(p1.getId()).size();
		assertThat(workersNumber5).isEqualTo(9);
	}

	@Test
	void testActionsGetTurn() throws Exception {
		gh.actions(p2, "GetHelp");
		assertThat(p2.getTurn()).isEqualTo(2);
	}
}
