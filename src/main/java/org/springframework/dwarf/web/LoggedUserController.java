package org.springframework.dwarf.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Controller
public class LoggedUserController {
	
	private static PlayerService playerService;
	
	@Autowired
	public LoggedUserController(PlayerService playerService) {
		LoggedUserController.playerService = playerService;
	}

	@GetMapping("/currentsession")
	public String showCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				System.out.println("------------------------------------------------");
				System.out.println("USER LOGGED: " + user.toString());
				System.out.println("------------------------------------------------");
				
			} else {
				System.out.println("User not auth");
			}
		}
		return "welcome";
	}
	
	public static String returnLoggedUserName() {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				return user.getUsername();
			}
			
		}
		return null;
	}
	
	public static Player loggedPlayer() {
		String playerUsername = LoggedUserController.returnLoggedUserName();
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

