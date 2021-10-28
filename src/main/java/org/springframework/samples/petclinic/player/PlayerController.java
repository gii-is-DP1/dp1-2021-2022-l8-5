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
package org.springframework.samples.petclinic.player;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */

@Controller
@RequestMapping("/players")
public class PlayerController {
	
	private static final String VIEWS_PLAYER_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

	private PlayerService playerService;

	@Autowired
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping()
	public String listPlayers(ModelMap modelMap) {
		String view = "players/listPlayers";
		Iterable<Player> players = playerService.findAll();
		modelMap.addAttribute("players", players);
		return view;

	}
	
	@GetMapping(path="/delete/{playerId}")
	public String deletePlayer(@PathVariable("playerId") Integer playerId,ModelMap modelMap) {
		String view = "players/listPlayers";
		Optional<Player> player = playerService.findByPlayerId(playerId);
		if (player.isPresent()) {
			playerService.delete(player.get());
			modelMap.addAttribute("message", "Player deleted!");
		} else {
			modelMap.addAttribute("message", "Player not found!");
		}
		return view;

	}
	
	
	@GetMapping(value = "/update/{playerId}")
	public String initUpdateOwnerForm(@PathVariable("playerId") int playerId, Model model) {
		Player player = this.playerService.findByPlayerId(playerId).get();
		model.addAttribute(player);
		return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/update/{playerId}")
	public String processUpdateOwnerForm(@Valid Player player, BindingResult result,
			@PathVariable("playerId") int playerId) {
		if (result.hasErrors()) {
			return VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}
		else {
			player.setId(playerId);
			this.playerService.savePlayer(player);
			return "redirect:/players";
		}
	}
}
