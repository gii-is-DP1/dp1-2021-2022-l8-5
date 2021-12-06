package org.springframework.dwarf.util;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Controller
public class CorrentUserController {
	
	@Autowired
	static PlayerService playerService;

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
	
	public static String returnCurrentUserName() {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				return user.getUsername();
			}
			
		}
		return null;
	}
	
	
	public static Player returnCurrentPlayer() {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				return playerService.findPlayerByUserName(user.getUsername());
			}
			
		}
		return null;
	}
	
	
}

