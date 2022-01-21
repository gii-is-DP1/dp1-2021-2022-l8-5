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
public class OrcRaidersTests {

	@Autowired
	protected OrcRaiders or;

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
		StrategyName name = or.getName();
		assertThat(name).isEqualTo(StrategyName.ORC_RAIDERS);

	}

	@Test
	@WithMockUser(username = "test")
	void testActionsNull() throws Exception {
		boolean initialCanResolveActions = game.getCanResolveActions();
		or.actions(null, "");
		boolean newCanResolveActions = game.getCanResolveActions();
		assertThat(initialCanResolveActions).isTrue();
		assertThat(newCanResolveActions).isFalse();
	}

	@Test
	@WithMockUser(username = "test")
	void testActionsPositive() throws Exception {
		Resources initialResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
		int initialBadges = initialResources.getBadges();
		or.actions(p1, "");
		int newBadges = initialResources.getBadges();
		assertThat(newBadges).isGreaterThan(initialBadges);
		assertThat(newBadges).isEqualTo(1);
	}

}
