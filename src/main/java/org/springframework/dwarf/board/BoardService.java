package org.springframework.dwarf.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Diego Ruiz Gil
 */
@Service
public class BoardService {
	
	private BoardRepository boardRepo;
	
	@Autowired
	public BoardService(BoardRepository boardRepo) {
		this.boardRepo = boardRepo;
	}		
	
	@Transactional
	public int boardCount() {
		return (int) boardRepo.count();
	}

	public Iterable<Board> findAll() {
		return boardRepo.findAll(); 
	}

	@Transactional(readOnly = true)
	public Optional<Board> findByBoardId(int id){
		return boardRepo.findById(id);
	}
	
	public void delete(Board board) {
		boardRepo.delete(board);
	}
	
	@Transactional
	public void saveBoard(Board board) throws DataAccessException {
		//creating board
		boardRepo.save(board);		

	}
}
