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
package org.springframework.dwarf.worker;


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
 * @author Jose Ignacio Garcia
 * @author David Zamora
 */

@Controller
@RequestMapping("/workers")
public class WorkerController {
	
	private static final String VIEWS_Worker_CREATE_OR_UPDATE_FORM = "workers/createOrUpdateWorkersForm";

	private WorkerService workersService;

	@Autowired
	public WorkerController(WorkerService workersService) {
		this.workersService = workersService;
	}

	@GetMapping()
	public String listWorkers(ModelMap modelMap) {
		String view = "workers/listWorkers";
		Iterable<Worker> Workers = workersService.findAll();
		modelMap.addAttribute("workers", Workers);
		return view;

	}
	/*
	@GetMapping(path="/delete/{WorkerId}")
	public String deleteWorker(@PathVariable("WorkerId") Integer WorkerId,ModelMap modelMap) {
		String view = "Workers/listWorkers";
		Optional<Worker> worker = workersService.findByWorkerId(WorkerId);
		if (Worker.isPresent()) {
			workersService.delete(worker.get());
			modelMap.addAttribute("message", "Worker deleted!");
		} else {
			modelMap.addAttribute("message", "Worker not found!");
		}
		return view;

	}*/
	
	
	@GetMapping(value = "/update/{workerId}")
	public String initUpdateOwnerForm(@PathVariable("workerId") int workerId, Model model) {
		Worker Worker = this.workersService.findByWorkerId(workerId).get();
		model.addAttribute(Worker);
		return VIEWS_Worker_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/update/{workerId}")
	public String processUpdateOwnerForm(@Valid Worker Worker, BindingResult result,
			@PathVariable("workerId") int WorkerId) throws IllegalPositionException {
		if (result.hasErrors()) {
			return VIEWS_Worker_CREATE_OR_UPDATE_FORM;
		}
		else {
			Worker.setId(WorkerId);
			this.workersService.saveWorker(Worker);
			return "redirect:/workers";
		}
	}
}
