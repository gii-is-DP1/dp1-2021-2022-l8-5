package org.springframework.dwarf.resources;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ResourcesRepository extends  CrudRepository<Resources, Integer>{
	
    @Query("SELECT resources FROM Resources resources WHERE resources.game.id =:gameId")
    public Collection<Resources> findByGameId(@Param("gameId") int gameId);
    
}
