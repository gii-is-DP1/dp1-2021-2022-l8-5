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
package org.springframework.dwarf.player2;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dwarf.user.AuthoritiesService;
import org.springframework.dwarf.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 */
@Controller
public class Player2Controller {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "players2/createOrUpdateOwnerForm";

	private final Player2Service playerService;

	@Autowired
	public Player2Controller(Player2Service playerService, UserService userService, AuthoritiesService authoritiesService) {
		this.playerService = playerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/players2/new")
	public String initCreationForm(Map<String, Object> model) {
		Player2 owner = new Player2();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players2/new")
	public String processCreationForm(@Valid Player2 owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.playerService.saveOwner(owner);
			
			return "redirect:/players2/" + owner.getId();
		}
	}

	@GetMapping(value = "/players2/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("players2", new Player2());
		return "players2/findOwners";
	}

	@GetMapping(value = "/players2")
	public String processFindForm(Player2 player2, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (player2.getLastName() == null) {
			player2.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Player2> results = this.playerService.findOwnerByLastName(player2.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "players2/findOwners";
		}
		else if (results.size() == 1) {
			// 1 owner found
			player2 = results.iterator().next();
			return "redirect:/players2/" + player2.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "players2/playersList";
		}
	}

	@GetMapping(value = "/players2/{player2}/edit")
	public String initUpdateOwnerForm(@PathVariable("player2id") int player2id, Model model) {
		Player2 player2 = this.playerService.findOwnerById(player2id);
		model.addAttribute(player2);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players2/{player2id}/edit")
	public String processUpdateOwnerForm(@Valid Player2 player2, BindingResult result,
			@PathVariable("player2id") int player2id) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			player2.setId(player2id);
			this.playerService.saveOwner(player2);
			return "redirect:/players2/{player2id}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/players2/{player2id}")
	public ModelAndView showOwner(@PathVariable("player2id") int ownerId) {
		ModelAndView mav = new ModelAndView("players/ownerDetails");
		mav.addObject(this.playerService.findOwnerById(ownerId));
		return mav;
	}

}