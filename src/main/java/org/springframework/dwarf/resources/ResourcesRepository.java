package org.springframework.dwarf.resources;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ResourcesRepository extends  CrudRepository<Resources, Integer>{
	
    @Query("SELECT resources FROM Resources resources WHERE resources.game.id =:gameId")
    public Collection<Resources> findByGameId(@Param("gameId") int gameId);
    
    @Query("SELECT resources FROM Resources resources WHERE resources.player.id =:playerId")
    public Collection<Resources> findByPlayerId(@Param("playerId") int playerId);

    @Query("SELECT resources FROM Resources resources WHERE resources.player.id =:playerId AND resources.game.id=:gameId")
    public Optional<Resources> findByPlayerIdAndGameId(@Param("playerId") int playerId,@Param("gameId") int gameId);
    
}
