package org.springframework.dwarf.worker;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface WorkerRepository extends  CrudRepository<Worker, Integer>{
	
    @Query("SELECT workers FROM Worker workers WHERE workers.player.id =:playerId")
    public Collection<Worker> findByPlayerId(@Param("playerId") int playerId);

    @Query("SELECT workers FROM Worker workers WHERE workers.player.id =:playerId AND workers.game.id =:gameId")
    public Collection<Worker> findByPlayerIdAndGameId(@Param("playerId") int playerId, @Param("gameId") int gameId);
    
    @Query("SELECT worker FROM Worker worker WHERE worker.player.id =:playerId AND worker.game.id =:gameId AND worker.status = false")
    public List<Worker> findNotPlacedByPlayerIdAndGameId(@Param("playerId") int playerId, @Param("gameId") int gameId);
    
    @Query("SELECT worker FROM Worker worker WHERE worker.game.id =:gameId AND worker.status = false")
    public List<Worker> findNotPlacedByGameId(@Param("gameId") int gameId);
    
    @Query("SELECT worker FROM Worker worker WHERE worker.game.id =:gameId AND worker.status = true")
    public List<Worker> findPlacedByGameId(@Param("gameId") int gameId);
    
}
