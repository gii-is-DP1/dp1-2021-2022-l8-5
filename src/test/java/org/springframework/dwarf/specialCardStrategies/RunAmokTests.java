package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class RunAmokTests {

	@Autowired
	protected RunAmok ra;

	@Autowired
	ResourcesService resourcesService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private GameService gameService;

	private Player p1;
	private Player p2;
	private Player p3;
	private Resources playerResources;
	private Game game;

	@BeforeEach
	void setup() throws Exception {

		game = gameService.findByGameId(2).get();

		boardService.createBoard(game);

		p1 = playerService.findPlayerById(4);
		p2 = playerService.findPlayerById(5);
		p3 = playerService.findPlayerById(2);

		playerResources = new Resources(game, p1);
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
		StrategyName name = ra.getName();
		assertThat(name).isEqualTo(StrategyName.RUN_AMOK);

	}

	@Test
	void testActions() {
		Board tablero = gameService.findBoardByGameId(game.getId()).get();
		List<MountainCard> cartasTableroInicial = tablero.getBoardCells().get(0).getMountaincards();

		ra.actions(p1, "");

		List<MountainCard> cartasTableroFinal = tablero.getBoardCells().get(0).getMountaincards();

		assertThat(cartasTableroInicial).isNotEqualTo(cartasTableroFinal);

	}

}
