package org.springframework.dwarf.forgesAlloy;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForgesAlloyResourcesRepository extends  CrudRepository<ForgesAlloyResources, Integer>{
	
	@Query("SELECT fa FROM ForgesAlloyResources fa WHERE fa.cardName=:cardName")
	ForgesAlloyResources findByCardName(@Param("cardName") String cardName) throws DataAccessException;
}
