package org.springframework.dwarf.board;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BoardCellRepository extends  CrudRepository<BoardCell, Integer>{
	List<BoardCell> findAll();
	
	@Query(value = "SELECT BC.* FROM BOARDCELLS BC JOIN BOARDS B WHERE B.ID=:boardId", nativeQuery = true)
	List<BoardCell> findAllByBoardId(@Param("boardId") Integer boardId)  throws DataAccessException;
	
	@Query(value = "SELECT BC.* FROM BOARDCELLS BC WHERE BC.BOARD_ID=:boardId AND BC.XPOSITION =:xposition AND BC.YPOSITION =:yposition", nativeQuery = true)
	BoardCell findByPosition(@Param("xposition") Integer xposition, @Param("yposition") Integer yposition, @Param("boardId") Integer boardId)  throws DataAccessException;
	
	@Query(value = "SELECT BC.* FROM BOARDCELLS BC JOIN BOARDS B WHERE B.ID=:boardId AND BC.OCCUPIED_BY_ID IS NOT NULL", nativeQuery = true)
	List<BoardCell> findOccupiedByBoardId(@Param("boardId") Integer boardId)  throws DataAccessException;
}
