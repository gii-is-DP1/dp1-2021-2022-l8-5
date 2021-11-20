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
 * @author Jose Ignacio Garcia
 */

@Controller
@RequestMapping("/resources")
public class ResourcesController {
	
	private static final String VIEWS_RESOURCES_CREATE_OR_UPDATE_FORM = "resources/createOrUpdateResourcesForm";

	private ResourcesService resourcesService;

	@Autowired
	public ResourcesController(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}

	@GetMapping()
	public String listResources(ModelMap modelMap) {
		String view = "resources/listResources";
		Iterable<Resources> resources = resourcesService.findAll();
		modelMap.addAttribute("resources", resources);
		return view;

	}
	



	//UPDATE

	@GetMapping(value = "/{resourcesId}/edit")
	public String initUpdateOwnerForm(@PathVariable("resourcesId") int resourcesId, Model model) {
		Resources resources = this.resourcesService.findByResourcesId(resourcesId).get();
		model.addAttribute(resources);
		return VIEWS_RESOURCES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/{resourcesId}/edit")
	public String processUpdateOwnerForm(@Valid Resources resources, BindingResult result,
			@PathVariable("resourcesId") int resourcesId) {
		if (result.hasErrors()) {
			return VIEWS_RESOURCES_CREATE_OR_UPDATE_FORM;
		}
		else {
			resources.setId(resourcesId);
			this.resourcesService.saveResources(resources);
			return "redirect:/resources";
		}
	}
}
