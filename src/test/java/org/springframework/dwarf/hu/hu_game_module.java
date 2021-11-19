package org.springframework.dwarf.hu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.dwarf.user.User;

public class hu_game_module {
	
	@Autowired
	private GameService gameser;
	@Autowired
	private PlayerService playerser;
	
	@Test
	@Transactional
	public void searchGameTest() {
		Game game= new Game();
		Player player1 = playerser.findPlayerById(1);
		Player player2 = playerser.findPlayerById(2);
		Player player3 = playerser.findPlayerById(3);
		game.setFirstPlayer(player1);
		game.setSecondPlayer(player2);
		game.setThirdPlayer(player3);

		
	}
	
	
	
	
//	Collection<Player> players = this.playerService.findPlayerByLastName("Schultz");
//	int found = players.size();
//
//	Player player = new Player();
//	player.setFirstName("Sam");
//	player.setLastName("Schultz");
//	player.setAvatarUrl("https://www.w3schools.com/w3images/avatar2.png");
//            User user=new User();
//            user.setUsername("Sam");
//            user.setPassword("supersecretpassword");
//            user.setEnabled(true);
//            player.setUser(user);                
//            
//	this.playerService.savePlayer(player);
//	assertThat(player.getId().longValue()).isNotEqualTo(0);
//
//	players = this.playerService.findPlayerByLastName("Schultz");
//	assertThat(players.size()).isEqualTo(found + 1);

}
