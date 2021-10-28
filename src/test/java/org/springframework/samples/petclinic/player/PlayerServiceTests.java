package org.springframework.samples.petclinic.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {
	
	@Autowired
	private PlayerService playerService;
	
	
	@Test
	public void testCountWithInitialData() {
		int count = playerService.playerCount();
		assertEquals(count,1);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Player> playerList = playerService.findAll();
		System.out.println("------------TEST FIND ALL------------");
		for(Player p : playerList) {
			System.out.println("Player username: " + p.getUsername());
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testFindByPlayerId() {
		int id = 1;
		
		Optional<Player> player = playerService.findByPlayerId(id);
		System.out.println("------------TEST FIND BY PLAYER ID------------");
		Player p = player.orElse(null);
		//assertEquals(p, null);
		
		if(p != null) {
			System.out.println("Player username with id: " + id + ", : " + p.getUsername());
		}else {
			System.out.println("Player not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testSavePlayer() {
		Player playerTest = new Player();
		playerTest.setUsername("Pene1");
		playerTest.setPassword("sincontraseña");
		//playerTest.setEnabled(true);
		playerTest.setTotalPoints(69);
		
		int id = playerTest.getId();
		playerService.savePlayer(playerTest);
		
		Optional<Player> player = playerService.findByPlayerId(id);
		System.out.println("------------TEST SAVE PLAYER------------");
		Player p = player.orElse(null);
		if(p != null) {
			System.out.println("Player username with id " + id + " : " + p.getUsername());
		}else {
			System.out.println("Player not found");
		}
		System.out.println("------------------------");
	}
	
	@Test
	public void testDeletePlayer() {
		int id = 2;
		
		Optional<Player> player = playerService.findByPlayerId(id);
		System.out.println("------------TEST DELETE PLAYER------------");
		Player p = player.orElse(null);
		if(p != null) {
			playerService.delete(p);
			Player deletedPlayer = playerService.findByPlayerId(id).orElse(null);
			assertEquals(deletedPlayer, null);
		}else {
			System.out.println("Player not found");
		}
		System.out.println("------------------------");
	}
}
