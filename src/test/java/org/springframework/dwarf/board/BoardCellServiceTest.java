package org.springframework.dwarf.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter({Service.class,Component.class}))
public class BoardCellServiceTest {
	
	@Autowired
	private BoardCellService boardCellService;
	
	@Autowired
	private MountainCardService mountainCardService;
	
	@Autowired
	private LoggedUserController loggedUserController;
	
	@Test
	@DisplayName("Save a board cell")
	void testSaveBoardCell() {
		long cellsFound = this.boardCellService.count();
		MountainCard card = this.mountainCardService.findByMountainCardId(1).get();
		Integer xposition = 1;
		Integer yposition = 0;
		BoardCell cell = new BoardCell(xposition, yposition, List.of(card));
		
		this.boardCellService.saveBoardCell(cell);
		
		long cellsAfterSave = this.boardCellService.count();
		
		assertThat(cellsFound+1).isEqualTo(cellsAfterSave);
	}
	
	@Test
	@DisplayName("Find all board cells")
	void testFindAllBoardCells() {
		Long boardCellsFound = this.boardCellService.findAll()
				.spliterator()
				.getExactSizeIfKnown();
		assertThat(boardCellsFound).isEqualTo(this.boardCellService.count());
	}
	
	@Test
	@DisplayName("Returns a board cell by its Id correctly")
	void testFindByBoardCellId() {
		MountainCard card = this.mountainCardService.findByMountainCardId(1).get();
		Integer xposition = 1;
		Integer yposition = 0;
		BoardCell cell = new BoardCell(xposition, yposition, List.of(card));
		
		this.boardCellService.saveBoardCell(cell);
		
		Optional<BoardCell> cellById = this.boardCellService.findByBoardCellId(cell.getId());
		
		assertThat(cellById.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("Create a board cell")
	void testCreateBoardCell() {
		Long cellsFound = this.boardCellService.count();
		Integer xposition = 2;
		Integer yposition = 0;
		
		this.boardCellService.createBoardCell(xposition, yposition);
		
		Long cellsAfterCreate = this.boardCellService.count();
		
		assertThat(cellsFound+1).isEqualTo(cellsAfterCreate);
		
	}
	
	@Test
	@DisplayName("Delete a board cell")
	void testDeleteBoardCell() {
		Integer xposition = 2;
		Integer yposition = 0;
		BoardCell boardCell = this.boardCellService.createBoardCell(xposition, yposition);
		
		Long cellsFound = this.boardCellService.count();
		
		this.boardCellService.delete(boardCell);
		
		Long cellsAfterDelete = this.boardCellService.count();
		
		assertThat(cellsFound-1).isEqualTo(cellsAfterDelete);
	}
}
