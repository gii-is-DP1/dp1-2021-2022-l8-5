package org.springframework.dwarf.game;


import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.player.Player;


/**
 * 
 * @autor Diego Ruiz gil
 */
public interface GameRepository extends  CrudRepository<Game, Integer>{
	
	/**
	 * Retrive all <code>Game</code>s which are looking for player from the data store
	 * @return a <code>Collection</code> of <code>Game</code>s
	 */
	@Query("SELECT game FROM Game game WHERE game.finishDate IS NULL AND (game.secondPlayer IS NULL OR game.thirdPlayer IS NULL)")
	List<Game> searchGamesToJoin() throws DataAccessException;
	
	/**
	 * Retrive all unfinished <code>Game</code>s
	 * @return a <code>Collection</code> of <code>Game</code>s
	 */
	@Query("SELECT game FROM Game game WHERE game.finishDate IS NULL")
	List<Game> searchUnfinishedGames() throws DataAccessException;

	@Query("SELECT game FROM Game game WHERE game.firstPlayer=:player OR game.secondPlayer=:player OR game.thirdPlayer=:player")
	List<Game> searchPlayerGames(@Param("player") Player player) throws DataAccessException;
	
	@Query("SELECT game FROM Game game WHERE (game.firstPlayer=:player OR game.secondPlayer=:player OR game.thirdPlayer=:player) AND game.finishDate IS NULL")
	Optional<Game> searchPlayerUnfinishedGames(@Param("player") Player player) throws DataAccessException;
	
	@Query("SELECT game FROM Game game WHERE (game.firstPlayer=:player OR game.secondPlayer=:player OR game.thirdPlayer=:player) AND game.finishDate IS NOT NULL")
	List<Game> searchPlayerFinishedGames(@Param("player") Player player) throws DataAccessException;

	//Busqueda p1,p2,p3
	@Query("SELECT g.firstPlayer FROM Game g WHERE g.id=:id")
	Player searchPlayerOneByGame(@Param("id") Integer id) throws DataAccessException;

	@Query("SELECT g.secondPlayer FROM Game g WHERE g.id=:id")
	Player searchPlayerTwoByGame(@Param("id") Integer id) throws DataAccessException;

	@Query("SELECT g.thirdPlayer FROM Game g WHERE g.id=:id")
	Player searchPlayerThreeByGame(@Param("id") Integer id) throws DataAccessException;
  
	@Query("SELECT board FROM Board board WHERE board.game.id=:gameId")
	Optional<Board> searchBoardByGameId(@Param("gameId") Integer gameId) throws DataAccessException;

	@Query("SELECT game.id FROM Game game WHERE (game.firstPlayer=:player OR game.secondPlayer=:player OR game.thirdPlayer=:player) AND game.finishDate IS NULL")
	Integer searchPlayerIsInGame(@Param("player") Player player) throws DataAccessException;

	@Query("SELECT board.mountainDeck FROM Board board WHERE board.game.id=:gameId")
	Optional<MountainDeck> searchDeckByGameId(@Param("gameId") Integer gameId) throws DataAccessException;
	
	
}
