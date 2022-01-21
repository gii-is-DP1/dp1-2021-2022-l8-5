package org.springframework.dwarf.mountainCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
@Import(LoggedUserController.class)
public class ShideTests {

	@Autowired
	protected Shide sh;

	@Autowired
	ResourcesService resourcesService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	private Player p1;
	private Player p2;
	private Player p3;

	private Game game;

	@BeforeEach
	void setup() throws Exception {

		game = gameService.findByGameId(2).get();
		game.setMusterAnArmyEffect(false);

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		p3 = playerService.findPlayerById(2);

		Resources playerResources = new Resources(game, p1);
		playerResources.addResource(ResourceType.GOLD, 2);
		resourcesService.saveResources(playerResources);

		Resources playerResources2 = new Resources(game, p2);
		playerResources.addResource(ResourceType.GOLD, 2);
		resourcesService.saveResources(playerResources2);

		Resources playerResources3 = new Resources(game, p3);
		playerResources.addResource(ResourceType.GOLD, 2);
		resourcesService.saveResources(playerResources3);
	}

	@Test
	void testGetName() {
		StrategyName name = sh.getName();
		assertThat(name).isEqualTo(StrategyName.SHIDE);

	}

	@Test
	void testExchangeResources() throws Exception {

		Resources initialResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();

		int initialGold = initialResources.getGold();
		int initialIron = initialResources.getIron();
		sh.exchangeResources(p1, game);

		Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();

		int newGold = newResources.getGold();
		int newIron = newResources.getIron();

		assertThat(initialGold).isGreaterThan(newGold);
		assertThat(newIron).isGreaterThan(initialIron);

	}

	@Test
	@WithMockUser(username = "test")
	void testActions() throws Exception {
		sh.actions(p1, "");
		Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
		int NEWbadges = newResources.getBadges();

		assertThat(NEWbadges).isEqualTo(1);
	}

	@Test
	@WithMockUser(username = "test")
	void testActionsNull() throws Exception {
		Resources initialResources = resourcesService.findByPlayerIdAndGameId(4, game.getId()).get();
		int OGIron = initialResources.getIron();
		sh.actions(null, "");
		Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
		int NEWIron = newResources.getIron();

		assertThat(NEWIron).isNotEqualTo(OGIron);
		assertThat(NEWIron).isGreaterThan(OGIron);
	}

}
