package com.mywork.ignite.demo.node1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mywork.ignite.demo.Person;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping("/person/{id}")
	public Person getCountriesByPopulation(@PathVariable Integer id) {
		return personService.getPerson(id);
	}

	@PutMapping("/person")
	public void updateCityPopulation(@RequestBody Person person) {		
		personService.createOrUpdatePerson(person);
	}

}
