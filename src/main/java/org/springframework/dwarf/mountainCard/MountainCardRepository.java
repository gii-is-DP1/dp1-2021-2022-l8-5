package org.springframework.dwarf.mountainCard;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface MountainCardRepository extends  CrudRepository<MountainCard, Integer>{
	
	@Query("SELECT card FROM MountainCard card WHERE card.group =:cardgroup")
	List<MountainCard> findByGroupCards(@Param("cardgroup") Integer cardGroup) throws DataAccessException;
	
	// card with cardgroup 1 and position given
	@Query("SELECT card FROM MountainCard card WHERE card.group = 1 AND card.xPosition =:xposition AND card.yPosition=:yposition")
	MountainCard findInitialCardByPosition(@Param("xposition") Integer xposition, @Param("yposition") Integer yposition);
}
