package org.springframework.dwarf.userStoriesGameModule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.game.GameController;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class userStoriesGameModuleTest {

    private PlayerService playerService;
    private GameService gameService;
    private GameController gameController;

    @Autowired
    public userStoriesGameModuleTest(PlayerService playerService, GameService gameService, GameController gameController){
        this.playerService = playerService;
        this.gameService = gameService;
        this.gameController = gameController;
    }
    /*
    // H1 - Búsqueda de partida

    // H1+E1
    
    @Test
    void searchAndJoinGameSuccesful(){
        //Player player1 = playerService.findPlayerById(1);
    	System.out.println("----------- TEST FIND ALL PLAYERS -----------------");
        for(Player player: playerService.findAll()){
            System.out.println("Player found: " + player.getUsername());
        }
        
        System.out.println("----------------------------------------------------");
    }
    
    // H1-E1
    @Test
    void searchAndJoinGameUnsuccesful(){
        Player player1 = playerService.findPlayerById(playerService.);
    }*/

}
