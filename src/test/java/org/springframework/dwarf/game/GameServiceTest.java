package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
	
	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;
	
	@Test
	@DisplayName("Returns the number of games created")
	void testGamesCount() {
		int totalGames = gameService.gamesCount();
		assertThat(totalGames).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Returns a game by its Id correctly")
	void testFindGameWithCorrectId() {
		Optional<Game> game = this.gameService.findByGameId(3);
		if(game.isPresent()) {
			assertThat(game.get().getFinishDate()).isEqualTo(LocalDateTime.of(2021, 11, 12, 17, 42, 0, 0));
			assertThat(game.get().getFirstPlayer().getUsername()).isEqualTo("dieruigil");
		}else {
			System.out.println("Game not found");
		}
	}
	
	@Test
	@DisplayName("Returns all games")
	void testFindAll() {
		Iterable<Game> games = gameService.findAll();
		assertThat(games.spliterator().getExactSizeIfKnown()).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Returns a list of games you can join")
	void testFindGamesToJoin() {
		List<Game> gamesToJoin = gameService.findGamesToJoin();
		for(Game game : gamesToJoin) {
			assertThat(game.getFinishDate()).isNull();
		}
	}

	@Test
	@DisplayName("Returns the player games")
	void testFindPlayerGames() {
		Player player = playerService.findPlayerById(1);
		List<Game> playerGames = gameService.findPlayerGames(player);

		assertThat(playerGames.size()).isEqualTo(1);
	}
//	@Test
//	@DisplayName("Return the player game")
//	void testFindPlayerGame() {
//		Player player =playerService.findPlayerById(0)
//	}
//	
	
	@Test
	@DisplayName("Returns all the players from the game")
	void searchPlayersByGame() {
		Integer gameId = 3;
		List<Player> players = gameService.searchPlayersByGame(gameId);
		assertEquals(players.get(1).getId(), 1); //En el game 3, el segundo jugador tiene el id 1
	}
	
    @Test
    @DisplayName("Returns the player one (p1) from a game")
    void testSearchPlayerOne() throws Exception {
        Player p = gameService.searchPlayerOneByGame(1);	//Juego sin terminar
        assertEquals(p.getId(), 6);
    }
    
    @Test
    @DisplayName("Returns the player two (p2) from a game")
    void testSearchPlayerTwo() throws Exception {
        Player p = gameService.searchPlayerTwoByGame(2);
        assertEquals(p.getId(), 5);
    }
    
    @Test
    @DisplayName("Returns the player three (p3) from a game")
    void testSearchPlayerThree() throws Exception {
        Player p = gameService.searchPlayerThreeByGame(3);	//Juego terminado
        assertEquals(p.getId(), 2);
    }
	
	@Test
	@DisplayName("Returns the player games which are not finished")
	void testFindPlayerUnfinishedGames() {
		Player player = playerService.findPlayerById(6);
		Game playerGame = gameService.findPlayerUnfinishedGames(player).get();

		assertThat(playerGame.getId().equals(1));
	}
	
	@Test
	@DisplayName("Returns the player games which are finished")
	void testFindPlayerFinishedGames() {
		Player player = playerService.findPlayerById(1);
		List<Game> playerGames = gameService.findPlayerFinishedGames(player);

		assertThat(playerGames.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("Save a game")
	void testSaveGame() throws Exception {
		Game game = new Game();
		Player player = playerService.findPlayerById(1);
		
		game.setFirstPlayer(player);
		game.setCurrentPlayer(player);
		
		gameService.saveGame(game);
		
		Integer gameId = game.getId();
		
		Optional<Game> gameSaved = gameService.findByGameId(gameId);
		assertThat(gameSaved.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("Join a game")
	void testJoinGame() throws Exception {
		// game with id 1 has just one player
		Game game = gameService.findByGameId(1).get();
		// player with id 3 is not in an unfinished game
		Player player = playerService.findPlayerById(3);
		
		gameService.joinGame(game, player);
		
		assertThat(game.getSecondPlayer().getId()).isEqualTo(player.getId());
	}
	
	@Test
	@DisplayName("The player is already in another unfinished game exception")
	void testCreateGameWhilePlayingException() {
		Game game = new Game();
		// player with id 6 is already in an unfinished game
		Player player = playerService.findPlayerById(6);
		
		game.setFirstPlayer(player);
		game.setCurrentPlayer(player);
		
		Assertions.assertThrows(CreateGameWhilePlayingException.class, () ->{
			gameService.saveGame(game);
		});
	}
	
	@Test
	@DisplayName("Delete a game")
	void testDeleteGame() {
		int gameId = 3;
		
		Optional<Game> game = gameService.findByGameId(gameId);
		
		if(game.isPresent()) {
			gameService.delete(game.get());
			Optional<Game> gameDeleted = gameService.findByGameId(gameId);
			assertThat(gameDeleted.isPresent()).isFalse();
		}else {
			System.out.println("Game not found");
		}
	}
	
	@Test
	@DisplayName("Exit game")
	void testExitGame() {
		Game game = gameService.findByGameId(2).get();
		// player with id 5 is in game 2
		Player player = playerService.findPlayerById(5);
		
		gameService.exit(game, player);
		
		assertThat(game.getSecondPlayer()).isNull();
	}
	
    @Test
    @DisplayName("Search the mountain deck of the game")
    void testSearchDeckByGameId() throws Exception {
        Optional<MountainDeck> mountainDeck = gameService.searchDeckByGameId(1);
        assertThat(mountainDeck.isPresent()).isTrue();
    }
}
