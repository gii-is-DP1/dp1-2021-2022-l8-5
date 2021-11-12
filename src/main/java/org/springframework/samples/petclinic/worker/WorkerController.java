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
package org.springframework.samples.petclinic.worker;


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
 * @author Jose Ingacio
 * @author David Zamora
 */

@Controller
@RequestMapping("/workers")
public class WorkerController {
	
	private static final String VIEWS_Worker_CREATE_OR_UPDATE_FORM = "workers/createOrUpdateWorkerForm";

	private WorkerService WorkerService;

	@Autowired
	public WorkerController(WorkerService WorkerService) {
		this.WorkerService = WorkerService;
	}

	@GetMapping()
	public String listWorkers(ModelMap modelMap) {
		String view = "Workers/listWorkers";
		Iterable<Worker> Workers = WorkerService.findAll();
		modelMap.addAttribute("Workers", Workers);
		return view;

	}
	/*
	@GetMapping(path="/delete/{WorkerId}")
	public String deleteWorker(@PathVariable("WorkerId") Integer WorkerId,ModelMap modelMap) {
		String view = "Workers/listWorkers";
		Optional<Worker> worker = WorkerService.findByWorkerId(WorkerId);
		if (Worker.isPresent()) {
			WorkerService.delete(worker.get());
			modelMap.addAttribute("message", "Worker deleted!");
		} else {
			modelMap.addAttribute("message", "Worker not found!");
		}
		return view;

	}*/
	
	
	@GetMapping(value = "/update/{WorkerId}")
	public String initUpdateOwnerForm(@PathVariable("WorkerId") int WorkerId, Model model) {
		Worker Worker = this.WorkerService.findByWorkerId(WorkerId).get();
		model.addAttribute(Worker);
		return VIEWS_Worker_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/update/{WorkerId}")
	public String processUpdateOwnerForm(@Valid Worker Worker, BindingResult result,
			@PathVariable("WorkerId") int WorkerId) {
		if (result.hasErrors()) {
			return VIEWS_Worker_CREATE_OR_UPDATE_FORM;
		}
		else {
			Worker.setId(WorkerId);
			this.WorkerService.saveWorker(Worker);
			return "redirect:/Workers";
		}
	}
}
