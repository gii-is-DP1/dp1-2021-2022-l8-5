package org.springframework.dwarf.specialCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.board.BoardCell;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.mountain_card.MountainCard;
import org.springframework.dwarf.mountain_card.MountainCardService;
import org.springframework.dwarf.mountain_card.MountainDeck;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.special_card.SpecialDeck;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
public class HoldACouncilTests {

   @Autowired
   protected HoldACouncil hac;
    
   @Autowired
   ResourcesService resourcesService;
    
   @Autowired
   private PlayerService playerService;
   
   @Autowired
   private GameService gameService;

   @Autowired
   private BoardService boardService;

   @Autowired
   private MountainCardService mountainCardService;
   
   
   private Player p1;
   private Game game;
   private Board board;

   @BeforeEach
   void setup() throws Exception {

       game = gameService.findByGameId(2).get();
       
       p1 = playerService.findPlayerById(2);
       
       board = boardService.createBoard(game);
       List<MountainCard> listacartas = new ArrayList<MountainCard>();
       listacartas.add(mountainCardService.findByMountainCardId(1).get());
       listacartas.add(mountainCardService.findByMountainCardId(10).get());
       for(BoardCell c : board.getBoardCells()) {
           c.setMountaincards(listacartas);
       }
       //board.getBoardCell(1, 0).setMountaincards(listacartas);

   }

    @Test
    void testGetName() {
        StrategyName name = hac.getName();
        assertThat(name).isEqualTo(StrategyName.HOLD_A_COUNCIL);
 
    }
    

    @Test
    void testRemoveTopCards() throws Exception {

	
		List<BoardCell> boardCell1 = board.getBoardCells();
        
        List<MountainCard> removedCardsList = hac.removeTopCards(game, board);
        
        assertThat(!(removedCardsList.isEmpty()));
        List<BoardCell> boardCell2 = board.getBoardCells();
        assertThat(boardCell1!=boardCell2);
        

        removedCardsList = hac.removeTopCards(game, board);

        assertThat((removedCardsList.isEmpty()));
        List<BoardCell> boardCell3 = board.getBoardCells();
        assertThat(boardCell2!=boardCell3);

       }


       @Test
       @WithMockUser(username = "test") 
       void testActions() throws Exception {

        MountainDeck mountainDeck1 = board.getMountainDeck();     
        
        hac.actions(p1, "");

        MountainDeck mountainDeck2 = board.getMountainDeck();
        assertThat(mountainDeck1!=mountainDeck2);
        

        hac.actions(p1, "");


        MountainDeck mountainDeck3 = board.getMountainDeck();
        assertThat(mountainDeck2!=mountainDeck3);

          }
    
}
