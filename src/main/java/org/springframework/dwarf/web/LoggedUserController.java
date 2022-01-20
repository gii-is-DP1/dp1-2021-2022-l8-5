package org.springframework.dwarf.web;


import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Controller
public class LoggedUserController {
	
	private PlayerService playerService;
	
	@Autowired
	public LoggedUserController(PlayerService playerService) {
		this.playerService = playerService;
	}

	
	public  String returnLoggedUserName() {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				return user.getUsername();
			}
			
		}
		return null;
	}
	
	public  Player loggedPlayer() {
		String playerUsername = this.returnLoggedUserName();
		Player player;
		if (playerUsername == null) {
			player = new Player();
			player.setLastName("Not Logged");
			player.setFirstName("Guest");
		} else {
			player = playerService.findPlayerByUserName(playerUsername);
		}
		
		return player;
	}
	
}

