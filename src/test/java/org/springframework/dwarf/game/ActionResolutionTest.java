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
import org.springframework.dwarf.mountain_card.CardType;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
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
	

	

	
}
