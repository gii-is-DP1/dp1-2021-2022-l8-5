package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.mountain_card.MountainDeckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Diego Ruiz Gil
 */
@Service
public class BoardService {
	
	private BoardRepository boardRepo;
	private MountainDeckService mountainDeckSer;
	private BoardCellService boardCellSer;
	
	@Autowired
	public BoardService(BoardRepository boardRepo, MountainDeckService mountainDeckSer, BoardCellService boardCellSer) {
		this.boardRepo = boardRepo;
		this.mountainDeckSer = mountainDeckSer;
		this.boardCellSer = boardCellSer;
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
		boardRepo.save(board);		
	}
	
	@Transactional
	public Board createBoard(Game game) throws DataAccessException {
		MountainDeck mountainDeck = mountainDeckSer.createMountainDeck();
		List<BoardCell> cells = new ArrayList<BoardCell>();
		
		for(int xposition=1; xposition < 4; xposition++) {
			for(int yposition=0; yposition < 3; yposition++) {
				cells.add(boardCellSer.createBoardCell(xposition, yposition));
			}
		}
		
		Board board = new Board(cells, mountainDeck, game);
		
		boardRepo.save(board);
		
		return board;
	}
}
