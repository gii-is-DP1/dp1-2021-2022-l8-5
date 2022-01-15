package org.springframework.dwarf.board;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.stereotype.Service;

@Service
public class BoardCellService {
	
	private BoardCellRepository boardCellRep;
	private MountainCardService mountainCardSer;
	
	@Autowired
	public BoardCellService(BoardCellRepository boardCellRep, MountainCardService mountainCardSer) {
		this.boardCellRep = boardCellRep;
		this.mountainCardSer = mountainCardSer;
	}
	
	public long count() {
		return boardCellRep.count();
	}
	
	public Iterable<BoardCell> findAll() {
		return boardCellRep.findAll();
	}
	
	@Transactional
	public Optional<BoardCell> findByBoardCellId(int id) {
		return boardCellRep.findById(id);
	}
	
	// hacer test
	@Transactional
	public BoardCell findByPosition(Integer xposition, Integer yposition) {	
		return boardCellRep.findByPosition(xposition, yposition);
	}
	
	// hacer test
	public List<BoardCell> findOccupiedByBoardId(Integer boardId) {
		return boardCellRep.findOccupiedByBoardId(boardId);
	}
	
	public void delete(BoardCell boardCell) {
		boardCellRep.delete(boardCell);
	}
	
	@Transactional
	public void saveBoardCell(BoardCell boardCell) throws DataAccessException {
		boardCellRep.save(boardCell);
	}
	
	@Transactional
	public BoardCell createBoardCell(Integer xposition, Integer yposition) throws DataAccessException {
		MountainCard card = mountainCardSer.findInitialCardByPosition(xposition, yposition);
		List<MountainCard> cards = List.of(card);
		
		BoardCell boardCell = new BoardCell(xposition, yposition, cards);
		
		boardCellRep.save(boardCell);
		
		return boardCell;
	}

	
}
