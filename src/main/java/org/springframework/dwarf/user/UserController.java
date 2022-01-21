/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.dwarf.user;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_PLAYER_CREATE_FORM = "players/createOrUpdatePlayerForm";

	private final PlayerService playerService;

	@Autowired
	public UserController(PlayerService ps) {
		this.playerService = ps;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		User user = new User();
		player.setUser(user);
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar1.png");
		model.put("player", player);
		return VIEWS_PLAYER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Player player, BindingResult result) throws DataAccessException, DuplicatedEmailException, DuplicatedEmailException {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			try {
				//creating owner, user and authorities
				this.playerService.savePlayer(player);
				
				return "redirect:/";
			} catch (DuplicatedUsernameException dp) {
				result.rejectValue ("username", " duplicate", "Username already exists");
				return VIEWS_PLAYER_CREATE_FORM;
				}catch (DuplicatedEmailException dp) {
					result.rejectValue ("email", " duplicate", "Email already exists");
					return VIEWS_PLAYER_CREATE_FORM;
					}
			 catch (InvalidEmailException dp) {
					result.rejectValue ("email", " invalid", "can't be empty");
					return VIEWS_PLAYER_CREATE_FORM;
					}
		}
	}

}
