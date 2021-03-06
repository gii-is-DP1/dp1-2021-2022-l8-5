package org.springframework.dwarf.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.mountainCard.MountainDeck;
import org.springframework.dwarf.mountainCard.MountainDeckService;
import org.springframework.dwarf.specialCard.SpecialDeck;
import org.springframework.dwarf.specialCard.SpecialDeckService;
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
	private SpecialDeckService specialDeckSer;
	
	@Autowired
	public BoardService(BoardRepository boardRepo, MountainDeckService mountainDeckSer, BoardCellService boardCellSer, SpecialDeckService specialDeckSer) {
		this.boardRepo = boardRepo;
		this.mountainDeckSer = mountainDeckSer;
		this.boardCellSer = boardCellSer;
		this.specialDeckSer = specialDeckSer;
	}
	
	@Transactional
	public int boardCount() {
		return (int) boardRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Board> findAll() {
		return boardRepo.findAll(); 
	}

	@Transactional(readOnly = true)
	public Optional<Board> findByBoardId(int id){
		return boardRepo.findById(id);
	}
	
	@Transactional
	public void delete(Board board) {
		for(BoardCell cell: board.getBoardCells()) {
			boardCellSer.delete(cell);
		}
		for(SpecialDeck specialDeck: board.getSpecialDecks()) {
			specialDeckSer.delete(specialDeck);
		}
		mountainDeckSer.delete(board.getMountainDeck());
		
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
		List<Integer> specialCardsId = this.randomList();
		List<SpecialDeck> specialDecks = new ArrayList<SpecialDeck>();
		
		for(int i=0;i<9;i=i+3) {
			specialDecks.add(specialDeckSer.createSpecialDeck(0, i/3, specialCardsId.subList(i, i+3)));
		}
		
		for(int yposition=0; yposition < 3; yposition++) {
			for(int xposition=1; xposition < 4; xposition++) {
				cells.add(boardCellSer.createBoardCell(xposition, yposition));
			}
		}
		
		Board board = new Board(cells, mountainDeck, game, specialDecks);
		
		boardRepo.save(board);
		
		//this.setCellsBoardId(cells, board);
		
		return board;
	}
	
	@Transactional(readOnly = true)
	public List<SpecialDeck> findSpecialDeckByBoardId(int id){
		return boardRepo.findSpecialDecksByBoardId(id);
	}
	
	private List<Integer> randomList(){
		Random rand = new Random();
        Integer limite = 9;
        List<Integer> cardIds = new ArrayList<Integer>();
        Integer id;

        for(int i=0;i<limite;i++) {
            id = rand.nextInt(limite)+1;
            while(cardIds.contains(id)) {
                id = rand.nextInt(limite)+1;
            }
            cardIds.add(id);
        }
        return cardIds;
	}
}
