package org.springframework.dwarf.board;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.mountainCard.MountainCard;
import org.springframework.dwarf.mountainCard.MountainCardService;
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
	
	@Transactional(readOnly = true)
	public long count() {
		return boardCellRep.count();
	}
	
	@Transactional(readOnly = true)
	public List<BoardCell> findAll() {
		return boardCellRep.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<BoardCell> findByBoardCellId(int id) {
		return boardCellRep.findById(id);
	}
	
	@Transactional(readOnly = true)
	public BoardCell findByPosition(Integer xposition, Integer yposition, Integer boardId) {	
		return boardCellRep.findByPosition(xposition, yposition, boardId);
	}
	
	@Transactional(readOnly = true)
	public List<BoardCell> findOccupiedByBoardId(Integer boardId) {
		return boardCellRep.findOccupiedByBoardId(boardId);
	}
	
	@Transactional(readOnly = true)
	public List<BoardCell> findAllByBoardId(Integer boardId) {
		return boardCellRep.findAllByBoardId(boardId);
	}
	
	@Transactional
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
