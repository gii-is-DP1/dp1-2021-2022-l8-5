package org.springframework.dwarf.board;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BoardCellRepository extends  CrudRepository<BoardCell, Integer>{
	
	@Query("SELECT bc FROM BoardCell bc WHERE bc.xposition =:xposition AND bc.yposition =:yposition")
	BoardCell findByPosition(@Param("xposition") Integer xposition, @Param("yposition") Integer yposition)  throws DataAccessException;
	
	@Query(value = "SELECT BC.* FROM BOARDCELLS BC JOIN BOARDS B WHERE B.ID=:boardId AND BC.OCCUPIED_BY_ID IS NOT NULL", nativeQuery = true)
	List<BoardCell> findOccupiedByBoardId(@Param("boardId") Integer boardId)  throws DataAccessException;
}
