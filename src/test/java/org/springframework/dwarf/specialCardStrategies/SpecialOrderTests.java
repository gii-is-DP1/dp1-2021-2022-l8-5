package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class SpecialOrderTests {

	@Autowired
	private SpecialOrder so;

	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private GameService gs;
	@Autowired
	private PlayerService playerService;

	private Player p1;
	private List<Resources> listaResources;
	private Optional<Game> g;

	@BeforeEach
	void setup() throws Exception {
		g = gs.findByGameId(2);
		p1 = playerService.findPlayerById(4);
		resourcesService.createPlayerResource(p1, g.get());

		listaResources = resourcesService.findByPlayerId(p1.getId()).stream().collect(Collectors.toList());
		for (Resources r : listaResources) {
			r.addResource(ResourceType.GOLD, 5);
			r.addResource(ResourceType.IRON, 5);
			r.addResource(ResourceType.STEEL, 5);
		}

	}

	@Test
	void testGiveRamdomResources() throws Exception {
		Map<ResourceType, Integer> ramdomResources = so.giveRandomResources();
		Integer amountResources = 0;
		amountResources = amountResources + ramdomResources.get(ResourceType.IRON).intValue();
		amountResources = amountResources + ramdomResources.get(ResourceType.GOLD).intValue();
		amountResources = amountResources + ramdomResources.get(ResourceType.STEEL).intValue();
		assertThat(amountResources).isEqualTo(-5);
	}

	@Test
	void testSetResourcesToPlayer() throws Exception {
		so.setResourcesToPlayer(listaResources.get(0));
		Integer amountResources = 0;
		for (Resources r : listaResources) {
			amountResources = amountResources + r.getGold();
			amountResources = amountResources + r.getIron();
			amountResources = amountResources + r.getSteel();
		}
		assertThat(amountResources).isLessThan(15);

	}

	@Test
	void testActions() throws Exception {
		so.actions(p1, "SpecialOrder");
		Resources resources = resourcesService.findByPlayerIdAndGameId(p1.getId(), g.get().getId()).get();
		assertThat(resources.getItems()).isEqualTo(1);
	}

	@Test
	void testGetName() {
		StrategyName name = so.getName();
		assertThat(name).isEqualTo(StrategyName.SPECIAL_ORDER);
	}

}
