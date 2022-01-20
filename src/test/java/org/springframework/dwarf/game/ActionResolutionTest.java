package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
public class ActionResolutionTest {
	
	@Autowired
	private ActionResolution actionResolution;
	
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
	@Autowired
	private MountainCardService mountainCardService;
	@Autowired
	private ResourcesService resourcesService;
	
	
	private Optional<Game> g;
	private Player loggedUser;
	private Player p1;
	private Player p2;
	private Player p3;
	private Board board;
	private Player currentPlayer;
	private MountainCard mountainCard;
	
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
		
		mountainCard = new MountainCard();	//seteo
		
		
		g.get().getPlayersList().stream().forEach(x -> x.setTurn(g.get().getPlayerPosition(x)+1));

		gameService.saveGame(g.get());
	}
	
	@Test
	void testCanResolveActionPositivo() {
		Game game = g.get();
		game.setCanResolveActions(false);				
		
		this.mountainCard.setCardType(CardType.AID);
		Boolean canResolve = actionResolution.canResolveAction(game, this.mountainCard);
		
		assertThat(canResolve).isEqualTo(true);
	}
	
	@Test
	void testCanResolveActionPositivo2() {		
		Game game = g.get();
		game.setCanResolveActions(true);	
		this.mountainCard.setCardType(CardType.MINE);
		Boolean canResolve = actionResolution.canResolveAction(game, this.mountainCard);
		
		assertThat(canResolve).isEqualTo(true);
	}
	
	@Test
	void testCanResolveActionPositivo3() {
		Game game = g.get();
		game.setCanResolveActions(true);
		this.mountainCard.setCardType(CardType.CRAFT);
		Boolean canResolve = actionResolution.canResolveAction(game, this.mountainCard);
		
		assertThat(canResolve).isEqualTo(true);
	}
	
	@Test
	void testCanResolveActionNegativo() {
		Game game = g.get();
		game.setCanResolveActions(false);
		this.mountainCard.setCardType(CardType.MINE);
		Boolean canResolve = actionResolution.canResolveAction(game, this.mountainCard);
		
		assertThat(canResolve).isEqualTo(false);
	}
	
	@Test
	void testHasFourItemsPositivo() throws Exception {
		Game game = g.get();
		
		resourcesService.createPlayerResource(p1, g.get());	//creacion recursos
		Resources resourcesP1 = resourcesService.findByPlayerIdAndGameId(p1.getId(), g.get().getId()).get();
		resourcesP1.setItems(4);
				
		Boolean theGameFinishes = actionResolution.hasFourItems(game);
		
		assertThat(theGameFinishes).isTrue();
	}
	
	@Test
	void testHasFourItemsNegativo() throws Exception {
		Game game = g.get();
		
		resourcesService.createPlayerResource(p1, g.get());	//creacion recursos
		Resources resourcesP1 = resourcesService.findByPlayerIdAndGameId(p1.getId(), g.get().getId()).get();
		resourcesP1.setItems(3); //<4
				
		Boolean theGameFinishes = actionResolution.hasFourItems(game);
		
		assertThat(theGameFinishes).isFalse();
	}
	
	@Test
	void testGetIsTiePositivo() {
		List<Integer> puntos = new ArrayList<>();
		puntos.add(6);
		puntos.add(2);
		puntos.add(6); //puntos p1, p2, p3
		
		//Hay empate
		Boolean empate = actionResolution.getIsTie(puntos);
		assertThat(empate).isTrue();
	}
	
	@Test
	void testGetIsTieNegativo() {
		List<Integer> puntos = new ArrayList<>();
		puntos.add(6);
		puntos.add(2);
		puntos.add(4); //puntos p1, p2, p3
		
		//No hay empate
		Boolean empate = actionResolution.getIsTie(puntos);
		assertThat(empate).isFalse();
	}

	
	@Test
	void testTieBreakerPositivo() {

		resourcesService.createPlayerResource(p1, g.get());
		resourcesService.createPlayerResource(p2, g.get());
		resourcesService.createPlayerResource(p3, g.get());
		
		Map<ResourceType, List<Integer>> map = actionResolution.getResourcesAmount(g.get());
		List<Integer> puntosIniciales = map.get(ResourceType.BADGES);
		
		puntosIniciales.set(0, 1);
		puntosIniciales.set(1, 2);
		puntosIniciales.set(2, 3);	//Forzamos que no haya empate
		
		List<Integer> puntosFinales = new ArrayList<>(puntosIniciales);
		puntosFinales = actionResolution.tieBreaker(puntosFinales, map);
		
		Set<Integer> comprobador = new HashSet<>(puntosFinales);
		
		assertThat(puntosFinales.size()).isEqualTo(comprobador.size());	//desempata por medallas (no hierro)
	}
	
	@Test
	void testTieBreakerPositivo2() {

		resourcesService.createPlayerResource(p1, g.get());
		resourcesService.createPlayerResource(p2, g.get());
		resourcesService.createPlayerResource(p3, g.get());
		
		resourcesService.findByPlayerIdAndGameId(p1.getId(), g.get().getId()).get().setIron(234);	
		//un player con hierro distinto
		
		Map<ResourceType, List<Integer>> map = actionResolution.getResourcesAmount(g.get());
		List<Integer> puntosIniciales = map.get(ResourceType.BADGES);
		
		puntosIniciales.set(0, 3);
		puntosIniciales.set(1, 0);
		puntosIniciales.set(2, 3);	//Forzamos que haya empate medallas
		
		List<Integer> puntosFinales = new ArrayList<>(puntosIniciales);
		puntosFinales = actionResolution.tieBreaker(puntosFinales, map);
		
		Set<Integer> comprobador = new HashSet<>(puntosFinales);
		
		assertThat(puntosFinales.size()).isEqualTo(comprobador.size());	//desempata por hierro (medallas =)
	}
	
	@Test
	void testTieBreakerNegativo() {

		resourcesService.createPlayerResource(p1, g.get());
		resourcesService.createPlayerResource(p2, g.get());
		resourcesService.createPlayerResource(p3, g.get());
		
		Map<ResourceType, List<Integer>> map = actionResolution.getResourcesAmount(g.get());
		List<Integer> puntosIniciales = map.get(ResourceType.BADGES);
		
		puntosIniciales.set(0, 3);
		puntosIniciales.set(1, 1);
		puntosIniciales.set(2, 3);	//Forzamos el empate en medallas
		
		List<Integer> puntosFinales = new ArrayList<>(puntosIniciales);
		puntosFinales = actionResolution.tieBreaker(puntosFinales, map);
		
		Set<Integer> comprobador = new HashSet<>(puntosFinales);
		
		assertThat(puntosFinales.size()).isNotEqualTo(comprobador.size());	//no tienen hierro que desempate (= medallas)
	}
	
}
