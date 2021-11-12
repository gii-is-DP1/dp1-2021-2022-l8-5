package org.springframework.dwarf.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dwarf.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	
		  List<Person> Persons =new ArrayList<Person>();
		  
		  List<String> nombres = List.of("Pablo ", "David ", "Pablo ", "Diego ", "Francisco Javier ", "Jose Ignacio ");
		  List<String> apellidos = List.of("Marin", "Zamora", "Alvarez", "Ruiz", "Migueles", "Garcia");
		  
		  for (int i=0; i<nombres.size();i++) {
			  Person person = new Person();
			  person.setFirstName(nombres.get(i));
			  person.setLastName(apellidos.get(i));
			  
			  Persons.add(person);
		  }
		  
		  model.put("persons", Persons);
		  model.put("title", "Dwarf");
		  model.put("group", "L8-5");
		  
		  
		  
	    return "welcome";
	  }
}
