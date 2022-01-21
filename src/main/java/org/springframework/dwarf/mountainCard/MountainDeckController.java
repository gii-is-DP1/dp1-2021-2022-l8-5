package org.springframework.dwarf.mountainCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Diego Ruiz Gil
 */
@Controller
@RequestMapping("/mountainDecks")
public class MountainDeckController {

	private MountainDeckService mountainDeckService;

	@Autowired
	public MountainDeckController(MountainDeckService mountainDeckService) {
		this.mountainDeckService = mountainDeckService;
	}

	@GetMapping()
	public String listMountainCards(ModelMap modelMap) {
		String view = "/mountainDeck/listMountainCards";
		Iterable<MountainDeck> mountainDeck = mountainDeckService.findAll();
		modelMap.addAttribute("mountainDeck", mountainDeck);
		return view;

	}
}
