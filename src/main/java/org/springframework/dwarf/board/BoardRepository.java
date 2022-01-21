package org.springframework.dwarf.board;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.dwarf.specialCard.SpecialDeck;

public interface BoardRepository extends  CrudRepository<Board, Integer>{
	
	//needs testing
	@Query("SELECT b.specialDecks FROM Board b WHERE b.id = :boardId")
	List<SpecialDeck> findSpecialDecksByBoardId(@Param("boardId") Integer boardId) throws DataAccessException;

}
