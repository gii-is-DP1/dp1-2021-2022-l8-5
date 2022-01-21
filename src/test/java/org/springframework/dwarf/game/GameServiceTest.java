package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.dwarf.worker.Worker;
import org.springframework.dwarf.worker.WorkerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value = { Service.class, Component.class }))
public class GameServiceTest {

	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;
	@Autowired
	protected BoardService boardService;
	@Autowired
	private LoggedUserController loggedUserController;
	@Autowired
	private WorkerService workerService;

	@Test
	@DisplayName("Returns the number of games created")
	void testGamesCount() {
		int totalGames = gameService.gamesCount();
		assertThat(totalGames).isEqualTo(3);
	}

	@Test
	@DisplayName("Returns a game by its Id correctly -  Positive")
	void testFindGameWithCorrectIdPositive() {
		Optional<Game> game = this.gameService.findByGameId(3);
		assertThat(game.get().getFirstPlayer().getUsername()).isEqualTo("dieruigil");
	}

	@Test
	@DisplayName("Returns a game by its Id correctly - Negative")
	void testFindGameWithCorrectIdNegative() {
		Optional<Game> game = this.gameService.findByGameId(5);
		assertThat(game.isEmpty());
		System.out.println("Game not found");
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
		for (Game game : gamesToJoin) {
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
	@DisplayName("Join a game as P2")
	void testJoinGameasP2() throws Exception {
		// game with id 1 has just one player
		Game game = gameService.findByGameId(1).get();
		// player with id 3 is not in an unfinished game
		Player player = playerService.findPlayerById(3);

		gameService.joinGame(game, player);

		assertThat(game.getSecondPlayer().getId()).isEqualTo(player.getId());
	}

	@Test
	@DisplayName("Join a game as P3")
	void testJoinGame() throws Exception {
		// game with id 1 has just one player
		Game game = gameService.findByGameId(1).get();
		// player with id 3 is not in an unfinished game
		Player player = playerService.findPlayerById(3);
		Player player2 = playerService.findPlayerById(1);

		gameService.joinGame(game, player);
		gameService.joinGame(game, player2);

		assertThat(game.getThirdPlayer().getId()).isEqualTo(player2.getId());
	}

	@Test
	@DisplayName("Join a game when already playing there")
	void testJoinGameJoinedAlready() throws Exception {
		// game with id 1 has just one player
		Game game = gameService.findByGameId(1).get();
		// player with id 3 is not in an unfinished game
		Player player = playerService.findPlayerById(3);

		gameService.joinGame(game, player);

		gameService.joinGame(game, player);

		assertThat(game.getSecondPlayer().getId()).isEqualTo(player.getId());

		assertThrows(NullPointerException.class, () -> {
			game.getThirdPlayer().getId();
		});

	}

	@Test
	@DisplayName("Join a game already full")
	void testJoinGameAlreadyFull() throws Exception {
		// game with id 1 has just one player
		Game game = gameService.findByGameId(2).get();
		// player with id 3 is not in an unfinished game
		Player player = playerService.findPlayerById(1);

		gameService.joinGame(game, player);

		assertThat(game.getFirstPlayer().getId()).isNotEqualTo(player.getId());
		assertThat(game.getSecondPlayer().getId()).isNotEqualTo(player.getId());
		assertThat(game.getThirdPlayer().getId()).isNotEqualTo(player.getId());

	}

	@Test
	@DisplayName("The player is already in another unfinished game exception")
	void testCreateGameWhilePlayingException() {
		Game game = new Game();
		// player with id 6 is already in an unfinished game
		Player player = playerService.findPlayerById(6);

		game.setFirstPlayer(player);
		game.setCurrentPlayer(player);

		Assertions.assertThrows(CreateGameWhilePlayingException.class, () -> {
			gameService.saveGame(game);
		});
	}

	@Test
	@DisplayName("Find Board By GameId- Positive")
	void testFindBoardByGameIdPositive() {
		Optional<Game> g = gameService.findByGameId(3);
		Optional<Board> boardId = boardService.findByBoardId(2);
		Optional<Board> board = gameService.findBoardByGameId(g.get().getId());
		assertThat(board.get().getId()).isEqualTo(boardId.get().getId());

	}

	@Test
	@DisplayName("Delete a game - Positive")
	void testDeleteGamePositive() {
		int gameId = 3;

		Optional<Game> game = gameService.findByGameId(gameId);
		gameService.delete(game.get());
		Optional<Game> gameDeleted = gameService.findByGameId(gameId);
		assertThat(gameDeleted.isPresent()).isFalse();
	}

	@Test
	@DisplayName("Delete a game - Negative")
	void testDeleteGameNegative() {
		int gameId = 5;

		Optional<Game> game = gameService.findByGameId(gameId);
		assertThat(game.isEmpty());
		System.out.println("Game not found");
	}

	@Test
	@DisplayName("Find Unfinished Games")
	void testFindUnfinishedGames() {

		List<Game> games = gameService.findUnfinishedGames();
		for (Game g : games) {
			assertThat(g.getFinishDate()).isNull();
		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 2 })
	@DisplayName("Exit game")
	void testExitGame(int currentPlayer) {
		Optional<Game> game = gameService.findByGameId(2);
		if (!gameService.findByGameId(2).isEmpty()) {
			Player player = playerService.findPlayerById(currentPlayer);
			gameService.exit(game.get(), player);
			assertThat(game.get().getThirdPlayer()).isNull();
		} else {
			System.out.println("Board not found");
		}
	}

	@Test
	@DisplayName("Search the mountain deck of the game")
	void testSearchDeckByGameId() throws Exception {
		Optional<MountainDeck> mountainDeck = gameService.searchDeckByGameId(1);
		assertThat(mountainDeck.isPresent()).isTrue();
	}

	@Test
	@DisplayName("Delete a game - Negative")
	void testDeleteGame() {
		int gameId = 5;

		Optional<Game> game = gameService.findByGameId(gameId);
		assertThat(game.isEmpty());
		System.out.println("Game not found");
	}

	@Test
	@DisplayName("Search the game a player is currently is")
	void testGetCurrentGameId() {
		Player player = playerService.findPlayerById(6);
		Integer gameId = gameService.getCurrentGameId(player);
		assertThat(gameId).isEqualTo(1);
	}

	@Test
	@DisplayName("Search the game a player is currently is - Negative")
	void testGetCurrentGameIdNegative() {
		Player player = playerService.findPlayerById(3);
		Integer gameId = gameService.getCurrentGameId(player);
		assertThat(gameId).isEqualTo(null);
	}

	@Test
	@DisplayName("Checks if a player is in a unfinished game")
	void testAlreadyInGame() {
		Player player = playerService.findPlayerById(3);
		Boolean res = gameService.alreadyInGame(player);
		assertFalse(res);
	}

	@Test
	@DisplayName("Checks if a player is in a unfinished game (2nd condition)")
	void testAlreadyInGame2() {
		Player player = playerService.findPlayerById(6);
		Boolean res = gameService.alreadyInGame(player);
		assertTrue(res);
	}

	@Test
	@DisplayName("Checks if a game is finished after executing finishGame")
	void testFinishGame() throws DataAccessException, CreateGameWhilePlayingException {
		Game game = gameService.findByGameId(2).get(); // Sin terminar
		boardService.createBoard(game); // le asociamos un tablero

		gameService.finishGame(game); // terminamos

		Collection<Worker> workerNotPlaced = workerService.findNotPlacedByGameId(game.getId());
		Collection<Worker> workerPlaced = workerService.findPlacedByGameId(game.getId());

		Board tablero = gameService.findBoardByGameId(game.getId()).orElse(null);

		assertThat(workerNotPlaced).isEmpty();
		assertThat(workerPlaced).isEmpty();
		assertThat(tablero).isNull();
	}

	@Test
	@DisplayName("Checks if all workers has been removed")
	void testDeleteAllWorkers() throws DataAccessException, CreateGameWhilePlayingException {
		Game game = gameService.findByGameId(2).get();
		gameService.deleteAllWorkers(game);

		Collection<Worker> workerNotPlaced = workerService.findNotPlacedByGameId(game.getId());
		Collection<Worker> workerPlaced = workerService.findPlacedByGameId(game.getId());

		assertThat(workerNotPlaced).isEmpty();
		assertThat(workerPlaced).isEmpty();
	}

	@Test
	@DisplayName("Check if player turns aren't null")
	void testSetPlayersTurns() {
		List<Player> players = new ArrayList<>();
		players.add(playerService.findPlayerById(1));
		players.add(playerService.findPlayerById(2));
		players.add(playerService.findPlayerById(3)); // jugadores

		gameService.setPlayersTurns(players);

		assertThat(players.get(0).getTurn()).isNotNull();
		assertThat(players.get(1).getTurn()).isNotNull();
		assertThat(players.get(2).getTurn()).isNotNull();
	}

	@Test
	@DisplayName("Returns all finished Games")
	void testFindFinishedGames() {
		Game gameFinished = gameService.findByGameId(3).get();

		List<Game> finishedGames = gameService.findFinishedGames();

		assertThat(finishedGames.size()).isOne();
		assertThat(finishedGames.get(0)).isEqualTo(gameFinished);
	}

	@Test
	@DisplayName("Return all non-finished games")
	void testFindCurrentGames() {
		List<Game> currentGames = gameService.findCurrentGames();
		assertThat(currentGames).isEmpty();
	}

}
