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
   
   
   private Player p1;
   private Player p2;
   private Player p3;
   private Resources playerResources;
   private Game game;
   private Board board;
   private Board board2;
   //private Board board3;

   @BeforeEach
   void setup() throws Exception {

       game = gameService.findByGameId(2).get();
       
       p1 = playerService.findPlayerById(6);
       p2 = playerService.findPlayerById(5);
       p3 = playerService.findPlayerById(2);
       
       board = boardService.findByBoardId(2).get();
   
       playerResources = new Resources(game, p1);
       playerResources.addResource(ResourceType.GOLD, 2);
       resourcesService.saveResources(playerResources);
       
       Resources playerResources2 = new Resources(game, p2);
       playerResources.addResource(ResourceType.GOLD, 2);
       resourcesService.saveResources(playerResources2);
       
       Resources playerResources3 = new Resources(game, p3);
       playerResources.addResource(ResourceType.GOLD, 2);
       resourcesService.saveResources(playerResources3);

       List<BoardCell> boardCells = new ArrayList<>();
       MountainDeck mountainDeck = new MountainDeck();
       Game game = new Game();
       List<SpecialDeck> specialDecks = new ArrayList<>();

       board2 = new Board(boardCells, mountainDeck, game, specialDecks);

      // board3 = boardService.createBoard(game);

   }

    @Test
    void testGetName() {
        StrategyName name = hac.getName();
        assertThat(name).isEqualTo(StrategyName.HOLD_A_COUNCIL);
 
    }
    

    @Test
    void testRemoveTopCards() throws Exception {

        List<MountainCard> removedCardsList = hac.removeTopCards(game, board);
        assertThat(!(removedCardsList.isEmpty()));

        removedCardsList = hac.removeTopCards(game, board2);
        assertThat((removedCardsList.isEmpty()));

        //removedCardsList = hac.removeTopCards(game, board3);
        //assertThat((removedCardsList.isEmpty()));


       }


       @Test
       @WithMockUser(username = "test") 
       void testActions() throws Exception {
        Game currentGame = gameService.findPlayerUnfinishedGames(p1).get();
		Board currentBoard = gameService.findBoardByGameId(currentGame.getId()).get();		
		MountainDeck oldMountainDeck = currentBoard.getMountainDeck();        
        hac.actions(p1, "");
        MountainDeck newMountainDeck = currentBoard.getMountainDeck();  
        assertThat(newMountainDeck!=oldMountainDeck);

          }
    
}
