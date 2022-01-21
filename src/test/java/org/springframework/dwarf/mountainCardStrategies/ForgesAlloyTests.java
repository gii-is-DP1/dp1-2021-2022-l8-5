package org.springframework.dwarf.mountainCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
class ForgesAlloyTests {

	@Autowired
	protected GameService gameService;

	@Autowired
	protected PlayerService playerService;

	@Autowired
	ResourcesService resourcesService;

	@Autowired
	protected ForgesAlloy fg;

	@BeforeEach
	void setup() throws Exception {
		Game g = gameService.findByGameId(2).get();
		Player p1 = playerService.findPlayerById(4);
		Player p2 = playerService.findPlayerById(5);
		g.getPlayersList().stream().forEach(x -> x.setTurn(g.getPlayerPosition(x) + 1));

		Resources playerResources = new Resources(g, p1);
		playerResources.addResource(ResourceType.STEEL, 2);

		Resources playerResources2 = new Resources(g, p2);
		playerResources.addResource(ResourceType.STEEL, 2);

		resourcesService.saveResources(playerResources);
		resourcesService.saveResources(playerResources2);
	}

	@Test
	void testChangePlayerNext() {
		Game g = gameService.findByGameId(2).get();
		List<Player> initialTurns = g.getTurnList();
		fg.changePlayerNext(g);
		List<Player> newTurns = g.getTurnList();
		assertThat(initialTurns).isNotEqualTo(newTurns);
	}

	@Test
	void testSetResourcesGivenRecived() {
		ForgesAlloyResources farOriginal = fg.far;
		fg.setResourcesGivenRecived("Forge Axe");
		ForgesAlloyResources farNew = fg.far;
		assertThat(farOriginal).isNotEqualTo(farNew);
	}

	@Test
	void testGetName() {
		fg.setResourcesGivenRecived("Forge Axe");
		StrategyName name = fg.getName();
		assertThat(name).isEqualTo(StrategyName.FORGES_ALLOY);

	}

	@Test
	void testCanResolveAction() throws Exception {
		fg.setResourcesGivenRecived("Forge Axe");
		Player p1 = playerService.findPlayerById(4);
		Game g = gameService.findByGameId(2).get();
		Resources playerResources = new Resources(g, p1);
		playerResources.addResource(ResourceType.STEEL, 2);
		Boolean res = fg.canResolveAction(playerResources);
		assertTrue(res);

	}

	@Test
	void testCanResolveActionNegative() throws Exception {
		fg.setResourcesGivenRecived("Forge Axe");
		Player p1 = playerService.findPlayerById(4);
		Game g = gameService.findByGameId(2).get();
		Resources playerResources = new Resources(g, p1);
		playerResources.addResource(ResourceType.STEEL, 1);
		Boolean res = fg.canResolveAction(playerResources);
		assertFalse(res);

	}

	@Test
	void testGiveResources() throws Exception {
		fg.setResourcesGivenRecived("Forge Axe");
		Player p1 = playerService.findPlayerById(4);
		Game g = gameService.findByGameId(2).get();
		Resources playerResources = new Resources(g, p1);

		playerResources.addResource(ResourceType.STEEL, 2);

		int initialValue = playerResources.getSteel();

		fg.giveResources(playerResources);

		int newValue = playerResources.getSteel();

		assertThat(initialValue).isGreaterThan(newValue);

	}

	@Test
	void testReceiveResources() throws Exception {
		fg.setResourcesGivenRecived("Forge Axe");
		Player p1 = playerService.findPlayerById(4);
		Game g = gameService.findByGameId(2).get();
		Resources playerResources = new Resources(g, p1);

		playerResources.addResource(ResourceType.STEEL, 2);

		int initialValue = playerResources.getItems();

		fg.receiveResources(playerResources);

		int newValue = playerResources.getItems();

		assertThat(initialValue).isLessThan(newValue);
	}

	@Test
	void testActions() throws Exception {
		Player p1 = playerService.findPlayerById(4);
		Game game = gameService.findPlayerUnfinishedGames(p1).get();
		List<Player> ogList = game.getTurnList();

		fg.actions(p1, "Forge Axe");

		List<Player> newList = game.getTurnList();

		assertThat(ogList).isNotEqualTo(newList);

	}

	@Test
	void testActionsCantDo() throws Exception {
		Player p1 = playerService.findPlayerById(4);
		Game game = gameService.findPlayerUnfinishedGames(p1).get();
		List<Player> ogList = game.getTurnList();

		Resources playerResources = resourcesService.findByPlayerIdAndGameId(4, 2).get();
		playerResources.addResource(ResourceType.STEEL, -2);

		resourcesService.saveResources(playerResources);

		fg.actions(p1, "Forge Axe");

		List<Player> newList = game.getTurnList();

		assertThat(ogList).isNotEqualTo(newList);

	}

	@Test
	void testActionsDontChangeOrder() throws Exception {
		Player p1 = playerService.findPlayerById(4);
		Game game = gameService.findPlayerUnfinishedGames(p1).get();
		List<Player> ogList = game.getTurnList();

		fg.actions(p1, "Alloy Steel");

		List<Player> newList = game.getTurnList();

		assertThat(ogList).isEqualTo(newList);

	}

	@Test
	void testActionsPlayer2() throws Exception {
		Player p1 = playerService.findPlayerById(5);
		Game game = gameService.findByGameId(2).get();
		List<Player> ogList = game.getTurnList();

		fg.actions(p1, "Forge Axe");

		List<Player> newList = game.getTurnList();

		assertThat(ogList).isEqualTo(newList);

	}

}
