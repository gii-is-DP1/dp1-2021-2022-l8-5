package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
	@DisplayName("")
	void testGamesCount() {
		int totalGames = gameService.gamesCount();
		assertThat(totalGames).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Returns a game by its Id correctly")
	void testFindGameWithCorrectId() {
		Optional<Game> game = this.gameService.findByGameId(3);
		assertThat(game.isPresent()).isTrue();
		assertThat(game.get().getFinishDate()).isEqualTo(LocalDateTime.of(2021, 11, 12, 17, 42, 0, 0));
		assertThat(game.get().getFirstPlayer().getUsername()).isEqualTo("dieruigil");
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
	@DisplayName("Save a game")
	void testSaveGame() {
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
}
