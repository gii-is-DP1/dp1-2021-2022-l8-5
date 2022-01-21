package org.springframework.dwarf.specialCard;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author FJ Migueles
 */
@Controller
@RequestMapping("/specialDecks")
public class SpecialDeckController {

	private SpecialDeckService specialDeckService;

		@Autowired
		public SpecialDeckController(SpecialDeckService specialDeckService) {
			this.specialDeckService = specialDeckService;
		}

		@GetMapping()
		public String listSpecialCards(ModelMap modelMap) {
			String view = "/specialDeck/listSpecialCards";
			Iterable<SpecialDeck> specialDeck = specialDeckService.findAll();
			modelMap.addAttribute("specialDeck", specialDeck);
			return view;

		}
}
