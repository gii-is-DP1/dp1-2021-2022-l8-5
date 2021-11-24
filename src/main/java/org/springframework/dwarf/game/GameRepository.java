package org.springframework.dwarf.game;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


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
}
