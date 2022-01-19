package org.springframework.dwarf.mountainCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class SeamTests {

	@Autowired
	protected Seam sm;

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

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		p3 = playerService.findPlayerById(2);

		Resources playerResources = new Resources(game, p1);
		playerResources.addResource(ResourceType.GOLD, 0);
		playerResources.addResource(ResourceType.IRON, 0);
		resourcesService.saveResources(playerResources);
	}

	@Test
	void testGetName() {
		StrategyName name = sm.getName();
		assertThat(name).isEqualTo(StrategyName.SEAM);

	}

	@Test
	void testSetResourcesIron() {
		sm.setResources("Iron Seam");
		ResourceType newType = sm.resource;
		Integer newAmountToAdd = sm.amountToAdd;
		assertThat(newType).isEqualTo(ResourceType.IRON);
		assertThat(newAmountToAdd).isEqualTo(3);

	}

	@Test
	void testSetResourcesGold() {
		sm.setResources("Gold Seam");
		ResourceType newType = sm.resource;
		Integer newAmountToAdd = sm.amountToAdd;
		assertThat(newType).isEqualTo(ResourceType.GOLD);
		assertThat(newAmountToAdd).isEqualTo(1);

	}

	@Test
	void testSetResourcesNegative() {
		sm.setResources("");
		ResourceType newType = sm.resource;
		Integer newAmountToAdd = sm.amountToAdd;
		if (newType.equals(ResourceType.IRON)) {
			assertThat(newAmountToAdd).isEqualTo(3);

		} else {
			assertThat(newAmountToAdd).isEqualTo(1);
		}

	}

	@Test
	void testActions() throws Exception {
		Resources initialResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
		Integer initialIron = initialResources.getIron();
		Integer initialGold = initialResources.getGold();
		sm.actions(p1, "Iron Seam");
		Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
		Integer newIron = newResources.getIron();
		Integer newGold = newResources.getGold();
		assertThat(initialIron).isEqualTo(0);
		assertThat(newIron).isEqualTo(3);

	}
}
