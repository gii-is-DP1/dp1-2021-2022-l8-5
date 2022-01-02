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
package org.springframework.dwarf.player;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.DuplicatedEmailException;
import org.springframework.dwarf.user.DuplicatedUsernameException;
import org.springframework.dwarf.user.InvalidEmailException;
import org.springframework.dwarf.user.User;
import org.springframework.dwarf.user.UserService;
import org.springframework.dwarf.web.LoggedUserController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * 
 * @author Pablo Marin
 * @autor Pablo Alvarez
 */
@Controller
public class PlayerController {

	private static final String VIEWS_PLAYER_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

	private final PlayerService playerService;
	private final GameService gameService;

	@Autowired
	public PlayerController(PlayerService playerService, UserService userService, AuthoritiesService authoritiesService, GameService gameService) {
		this.playerService = playerService;
		this.gameService = gameService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/players/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		player.setAvatarUrl("https://www.w3schools.com/w3images/avatar1.png");
		model.put("player", player);
		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players/new")
	public String processCreationForm(@Valid Player player, BindingResult result) throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				//creating owner, user and authorities
				this.playerService.savePlayer(player);
				
				return "redirect:/players";
			} catch (DuplicatedUsernameException dp) {
				result.rejectValue ("username", "duplicate", "already exists");
				return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
				
				}
			catch (DuplicatedEmailException dp) {
					result.rejectValue ("email", "duplicate", "already exists");
					return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
					}
			
			 catch (InvalidEmailException dp) {
					result.rejectValue ("email", " invalid", "can't be empty");
					return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
					}
			}
			

		}

	@GetMapping(value = "/players/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("players", new Player());
		return "players/findPlayers";
	}
	
	
	@GetMapping(value = "/players")
	public String processFindForm(Player player, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /players to return all records
		if (player.getLastName() == null) {
			player.setLastName(""); // empty string signifies broadest possible search
		}

		// find players by last name
		Collection<Player> results = this.playerService.findPlayerByLastName(player.getLastName());
		if (results.isEmpty()) {
			// no players found
			result.rejectValue("lastName", "notFound", "not found");
			model.put("players", new Player());
			return "players/findPlayers";
		}
		else {
			// multiple players found
			model.put("selections", results);
			return "players/playersList";
		}
	}

	@GetMapping(value = "/players/{playerid}/edit")
	public String initUpdateOwnerForm(@PathVariable("playerid") int playerid, Model model) {
		Player player = this.playerService.findPlayerById(playerid);
		model.addAttribute("player",player);
		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players/{playerid}/edit")
	public String processUpdatePlayerForm(@Valid Player player, BindingResult result,
			@PathVariable("playerid") int playerid) throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}
		else {
			return updatingPlayer(playerid, player, result);
		}
	}

	
	@GetMapping(value = "/editProfile")
	public String initUpdateMeForm(Model model) {
		String username = LoggedUserController.returnLoggedUserName();
		Player player = playerService.findPlayerByUserName(username);
		model.addAttribute("player",player);

		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/editProfile")
	public String processUpdateMeForm(@Valid Player player, BindingResult result) throws DataAccessException, DuplicatedUsernameException, DuplicatedEmailException {
		String username = LoggedUserController.returnLoggedUserName();
		Integer playerid = playerService.findPlayerByUserName(username).getId();
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}
		else {
			return updatingPlayer(playerid, player, result);
		}
		
	}
	
	private String updatingPlayer(Integer playerid,Player player,BindingResult result) {
		Player playerFound = playerService.findPlayerById(playerid);
		User userFound = playerFound.getUser();
		BeanUtils.copyProperties(player,playerFound,"id");
		User user2 = player.getUser();
		BeanUtils.copyProperties(user2,userFound,"username","authorities");
		playerFound.setUser(userFound);
		try {
			this.playerService.savePlayer(playerFound);
			
			return "redirect:/";
		} catch (DuplicatedUsernameException dp) {
			result.rejectValue ("username", " duplicate", "already exists");
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (DuplicatedEmailException dp) {
				result.rejectValue ("email", " duplicate", "already exists");
				return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
				}  catch (InvalidEmailException dp) {
					result.rejectValue ("email", " invalid", "can't be empty");
					return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
					}
		//this.playerService.savePlayer(player);
	}
	
	/**
	 * Custom handler for displaying an player.
	 * @param playerId the ID of the player to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/players/{playerid}")
	public ModelAndView showPlayer(@PathVariable("playerid") int playerId) {

		ModelAndView mav = new ModelAndView("players/playerDetails");
		Player player = this.playerService.findPlayerById(playerId);
		mav.addObject("player",player);
		mav.addObject("finishedGames", this.gameService.findPlayerFinishedGames(player));
		mav.addObject("currentGame", this.gameService.findPlayerUnfinishedGames(player).orElse(null));
		return mav;
	}
	
	@GetMapping("/myProfile")
	public ModelAndView showMyProfile() {
		String username = LoggedUserController.returnLoggedUserName();
		Integer playerid = playerService.findPlayerByUserName(username).getId();
		
		ModelAndView mav = showPlayer(playerid);
		return mav;

	}
	
	
	
	@GetMapping("/players/{playerId}/delete")
	public String deletePlayer(@PathVariable("playerId") Integer playerId,ModelMap modelMap) throws DeletePlayerInGameException {
		String view = "redirect:/players";
		Player player = playerService.findPlayerById(playerId);
		playerService.delete(player);
		modelMap.addAttribute("message", "Player deleted!");
		return view;
	}
	
	

}


