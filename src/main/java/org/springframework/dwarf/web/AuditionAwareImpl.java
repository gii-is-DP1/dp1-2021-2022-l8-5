package org.springframework.dwarf.web;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
@Component
public class AuditionAwareImpl  implements AuditorAware<String>{
	
	
	@Override
	public Optional<String> getCurrentAuditor() {
			Authentication auth =SecurityContextHolder.getContext().getAuthentication();
			Optional<String> currentUserName = Optional.empty();
			if (auth!=null) {
				if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
					User user = (User) auth.getPrincipal();
					currentUserName = Optional.of(user.getUsername());
					return currentUserName;
							
				}
				
			}
			return currentUserName;
	}

}
