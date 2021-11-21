package org.springframework.dwarf.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Controller
public class CorrentUserController {

	@GetMapping("/currentsession")
	public String showCurrentUser() {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				System.out.println(auth.getPrincipal().toString());
				
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
		return "";
	}
}

