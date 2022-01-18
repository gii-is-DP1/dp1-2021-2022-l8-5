package org.springframework.dwarf.mountainCardStrategies;



import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dwarf.card.StrategyName;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.resources.ResourceType;
import org.springframework.dwarf.resources.Resources;
import org.springframework.dwarf.resources.ResourcesService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(value= {Service.class, Component.class}))
@Import(LoggedUserController.class)
public class DragonsKnockersTests {
    

    @Autowired
    protected DragonsKnockers dK;
    
    @Autowired
    ResourcesService resourcesService;
    
   @Autowired
   private PlayerService playerService;
   
   @Autowired
   private GameService gameService;
   
   
   private Player p1;
   private Player p2;
   private Player p3;
   private Resources playerResources;
   private Game game;
    
    @BeforeEach
       void setup() throws Exception {

           game = gameService.findByGameId(2).get();

           
           p1 = playerService.findPlayerById(4);
           p2 = playerService.findPlayerById(5);
           p3 = playerService.findPlayerById(2);
           
       
           playerResources = new Resources(game, p1);
           playerResources.addResource(ResourceType.GOLD, 2);
           resourcesService.saveResources(playerResources);
           
           Resources playerResources2 = new Resources(game, p2);
           playerResources.addResource(ResourceType.GOLD, 2);
           resourcesService.saveResources(playerResources2);
           
           Resources playerResources3 = new Resources(game, p3);
           playerResources.addResource(ResourceType.GOLD, 2);
           resourcesService.saveResources(playerResources3);
       }
   
    @Test
   void testGetName() {
     StrategyName name = dK.getName();
     assertThat(name).isEqualTo(StrategyName.DRAGONS_KNOCKERS);
     
   }
    
    @Test
       void testSetResources() throws Exception {
        
        String greatDragon = "Great Dragon";
        String dragon = "Dragon";
        String knockers = "Knockers";
                
        dK.setResources(greatDragon);        
         assertThat(dK.resourceType==ResourceType.GOLD);
         assertThat(dK.amount==null);

        dK.setResources(dragon);        
         assertThat(dK.resourceType==ResourceType.GOLD);
         assertThat(dK.amount==-1);
        
         dK.setResources(knockers);        
         assertThat(dK.resourceType==ResourceType.IRON);
         assertThat(dK.amount==-1);
         
       }
       
    @Test
       void testRemoveResources() throws Exception {

        dK.amount= 1;
        dK.removeResources(p1,game);        
        assertThat(playerResources.getGold()==2);

        dK.amount=null;
        dK.removeResources(p1,game);        
        assertThat(playerResources.getGold()==null);

       }

   
    @Test
    @WithMockUser(username = "test") 
    void testActions() throws Exception {
            dK.actions(p1, "Great Dragon");
            Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
            int newBadges = newResources.getBadges();
             
           assertThat(newBadges).isEqualTo(1);
       }
        
    @Test
    @WithMockUser(username = "test") 
    void testActionsNull() throws Exception {
            dK.actions(null, "Great Dragon");
            Resources newResources = resourcesService.findByPlayerIdAndGameId(p1.getId(), game.getId()).get();
            int newGold = newResources.getGold();
                 
            assertThat(newGold).isEqualTo(0);
           }

}

