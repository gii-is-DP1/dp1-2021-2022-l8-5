package org.springframework.dwarf.resources;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dwarf.board.Board;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.User;
import org.springframework.stereotype.Service;

/**
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ResourcesServiceTest {
    
	private Player p = new Player();
	private Game g = new Game();
	/*
	@MockBean
	private PlayerService playerService;
	
	@MockBean
	private GameService gameService;
*/
	@BeforeEach
	void setup() {
		User u = new User();
		p.setUser(u);
		p.setId(6);
		g.setId(1);
		
		
	}
	
	@Autowired
	private ResourcesService resourcesService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = resourcesService.ResourcesCount();
		assertEquals(count,1);
	}

	@Test
	public void testFindAll() {
		Iterable<Resources> resources = this.resourcesService.findAll();
		assertEquals(resources.spliterator().getExactSizeIfKnown(), 1);
	}
	
	
	@Test
	public void testFindByResourcesId() {
		int id = 1;
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST FIND BY Resources ID------------");
		Resources p = Resources.orElse(null);	//Si no lo encuentra, devuelve null
		assertEquals(p.getGold(), 3);
	}



	@Test
	public void testCreatePlayerResource() {
	
		resourcesService.createPlayerResource(p,g);
		Optional<Resources> countAfter = resourcesService.findByPlayerIdAndGameId(p.getId(), g.getId());
		assertTrue(countAfter.isPresent());
		
		
	}
	
	@Test
	public void testCreatePlayerResourceNeg() {
		resourcesService.createPlayerResource(p,g);
		 assertThrows(DataIntegrityViolationException.class, () -> {
			 resourcesService.createPlayerResource(p,g);
		    });
		
		
	}
	
    @Test
    void findByGameId() throws Exception {
        Collection<Resources> resources = resourcesService.findByGameId(1);
        assertEquals(resources.spliterator().getExactSizeIfKnown(), 1);
    }
    
    @Test
    void findByPlayerId() throws Exception {
        Collection<Resources> resources = resourcesService.findByPlayerId(1);
        assertEquals(resources.spliterator().getExactSizeIfKnown(), 1);
    }

	@Test
    void findByPlayerIdAndGameId() throws Exception {
        Optional<Resources> resources = resourcesService.findByPlayerIdAndGameId(1,1);
        assertEquals(resources.get().getBadges(), 2);
    }
	
	@Test
	public void testSaveResources() {
		Resources ResourcesTest = new Resources();
		ResourcesTest.setIron(3);
        ResourcesTest.setSteel(3);
        ResourcesTest.setGold(3);
        ResourcesTest.setItems(3);
        ResourcesTest.setBadges(3);
		
		resourcesService.saveResources(ResourcesTest);
		int id = ResourcesTest.getId();
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST SAVE Resources------------");
		Resources p = Resources.orElse(null);
		assertEquals(p.getGold(), 3);

	}
	
	@Test
	public void testDeleteResources() {
		int id = 1;
		
		Optional<Resources> Resources = resourcesService.findByResourcesId(id);
		System.out.println("------------TEST DELETE Resources------------");
		Resources p = Resources.orElse(null);	

		if(p != null) {
			resourcesService.delete(p);
			Resources deletedResources = resourcesService.findByResourcesId(id).orElse(null);
			assertEquals(deletedResources, null);
		}else {
			System.out.println("Resources not found");
		}
		System.out.println("------------------------");
	}
	
	
	
}

