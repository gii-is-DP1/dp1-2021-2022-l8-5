package org.springframework.dwarf.userStories;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.GameController;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class userStoriesGameModuleTest {
	/*
	@Autowired
	private PlayerService playerService;
	@Autowired
	private GameService gameService;

	private GameController gameController;
	
	@BeforeEach
	void initialData() {
		gameController = new GameController(gameService, playerService);
	}
	
	// H1 - Búsqueda de partida

	// H1+E1
	@WithMockUser(username = "pabmargom3", password = "1")
	@Test
	@DisplayName("Search and join game succesfully")
	void searchAndJoinGameSuccesful(){
		Player player3 = playerService.findPlayerById(1);

		// First P: id 2, Second P: id 3, Third P: null
		Game game = gameService.findByGameId(2).get();

		this.gameController.joinGame(game.getId(), new ModelMap());

		assertEquals(game.getThirdPlayer(), player3);
	}
    
    
	// H1-E1
	@WithMockUser(username = "dieruigil", password = "1")
	@Test
	@DisplayName("Search and join game unsuccesfully")
	void searchAndJoinGameUnsuccesful(){

		// First P: id 3, Second P: id 1, Third P: id 2
		Game game = gameService.findByGameId(3).get();

		this.gameController.deleteGame(game.getId(), new ModelMap());

		Optional<Game> gameDeleted = gameService.findByGameId(3);

		assertFalse(gameDeleted.isPresent());
	}
    
    
	// H2 - Creación de partida

	// H2+E1
	@WithMockUser(username = "pabmargom3", password = "1")
	@Test
	@DisplayName("Game creation succesfully")
	void gameCreationSuccesful(){
		Integer gamesBeforeCreate = gameService.gamesCount();

		this.gameController.initCreateGame(new ModelMap());

		Integer gamesAfterCreate = gameService.gamesCount();

		assertNotEquals(gamesAfterCreate, gamesBeforeCreate);

	}
	
  	// H2-E1
  	@Test
  	@DisplayName("Game creation unsuccesfully")
  	void gameCreationUnsuccesful(){
    	Integer gamesBeforeCreate = gameService.gamesCount();

      	this.gameController.initCreateGame(new ModelMap());

      	Integer gamesAfterCreate = gameService.gamesCount();
  	}*/
}
