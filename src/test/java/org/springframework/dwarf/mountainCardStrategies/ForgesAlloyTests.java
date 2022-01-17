package org.springframework.dwarf.mountainCardStrategies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.forgesAlloy.ForgesAlloyResources;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
class ForgesAlloyTests {
	
		
	   @Autowired
		protected GameService gameService;
	   
       @Autowired
       protected PlayerService playerService;
       
       @Autowired
       protected  ForgesAlloy fg;
       
   @BeforeEach
   	void setup() {
	   Game g =  gameService.findByGameId(2).get();
	   g.getPlayersList().stream().forEach(x -> x.setTurn(g.getPlayerPosition(x)+1));
   	
   	}
	   
	   @Test
		void testChangePlayerNext() {
		  Game g =  gameService.findByGameId(2).get();
			List<Player> initialTurns = g.getTurnList();
			fg.changePlayerNext(g);
			List<Player> newTurns = g.getTurnList();
			assertThat(initialTurns).isNotEqualTo(newTurns);
		}

	   @Test
		void testSetResourcesGivenRecived() {
		  ForgesAlloyResources farOriginal = fg.far;
		  fg.setResourcesGivenRecived("Forge Axe");
		  ForgesAlloyResources farNew = fg.far;
			assertThat(farOriginal).isNotEqualTo(farNew);
		}
	   
	   @Test
		void testGetName() {
		  fg.setResourcesGivenRecived("Forge Axe");
		  StrategyName name = fg.getName();
		  assertThat(name).isEqualTo(StrategyName.FORGES_ALLOY);
		  
		}
	   

}
