package org.springframework.dwarf.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardCellRepository;
import org.springframework.dwarf.board.BoardCellService;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountainCardStrategies.Shide;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
public class MineralExtractionTests {
	
	
	 @Autowired
     protected MineralExtraction me;
	 
	 @Autowired
     protected BoardCellService bcs;
	 
	 @Autowired
	private PlayerService playerService;
	 
	 @Autowired
	private GameService gameService;
	 
	 @Autowired
	private BoardService bs;
		 
	 
	 private Player p1;
	 
	 @BeforeEach
		void setup() throws Exception {
	
			p1 = playerService.findPlayerById(4);

		}

	   @Test
		void testGetName() {
		  GamePhaseEnum name = me.getPhaseName();
		  assertThat(name).isEqualTo(GamePhaseEnum.MINERAL_EXTRACTION);
		  
		}
	   
	   @Test
		void testSetAndSaveBoardCell() {
		   Optional<Game> g =gameService.findByGameId(2);
           Board board = bs.createBoard(g.get());
           BoardCell bc = board.getBoardCell(1, 0);
		   bc.setOccupiedBy(p1);
		   //assertThat(bc.getOccupiedBy()).isNotNull();
		  me.setAndSaveBoardCell(bc);
		  bc = bcs.findByBoardCellId(1).get();
		  assertThat(bc.getOccupiedBy()).isNull();
		  
		}
	   
	   

}
