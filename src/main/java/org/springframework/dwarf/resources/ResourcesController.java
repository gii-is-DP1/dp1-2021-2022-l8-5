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
package org.springframework.dwarf.resources;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author David Zamora
 */

@Controller
@RequestMapping("/Resourcess")
public class ResourcesController {
	
	private static final String VIEWS_Resources_CREATE_OR_UPDATE_FORM = "Resourcess/createOrUpdateResourcesForm";

	private ResourcesService ResourcesService;

	@Autowired
	public ResourcesController(ResourcesService ResourcesService) {
		this.ResourcesService = ResourcesService;
	}

	@GetMapping()
	public String listResourcess(ModelMap modelMap) {
		String view = "Resourcess/listResourcess";
		Iterable<Resources> Resourcess = ResourcesService.findAll();
		modelMap.addAttribute("Resourcess", Resourcess);
		return view;

	}
	/*
	@GetMapping(path="/delete/{ResourcesId}")
	public String deleteResources(@PathVariable("ResourcesId") Integer ResourcesId,ModelMap modelMap) {
		String view = "Resourcess/listResourcess";
		Optional<Resources> Resources = ResourcesService.findByResourcesId(ResourcesId);
		if (Resources.isPresent()) {
			ResourcesService.delete(Resources.get());
			modelMap.addAttribute("message", "Resources deleted!");
		} else {
			modelMap.addAttribute("message", "Resources not found!");
		}
		return view;

	}*/
	
	
	@GetMapping(value = "/update/{ResourcesId}")
	public String initUpdateOwnerForm(@PathVariable("ResourcesId") int ResourcesId, Model model) {
		Resources Resources = this.ResourcesService.findByResourcesId(ResourcesId).get();
		model.addAttribute(Resources);
		return VIEWS_Resources_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/update/{ResourcesId}")
	public String processUpdateOwnerForm(@Valid Resources Resources, BindingResult result,
			@PathVariable("ResourcesId") int ResourcesId) {
		if (result.hasErrors()) {
			return VIEWS_Resources_CREATE_OR_UPDATE_FORM;
		}
		else {
			Resources.setId(ResourcesId);
			this.ResourcesService.saveResources(Resources);
			return "redirect:/Resourcess";
		}
	}
}
