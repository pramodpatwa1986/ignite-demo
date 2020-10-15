package com.mywork.ignite.demo.node2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	 @Autowired
	 PersonRepository personRepository;
	 
	 public void createOrUpdatePerson(Person person) {
		 personRepository.updatePerson(person.getId(), person);
	 }
	 
	 public Person getPerson(Integer id) {
		 return personRepository.getPerson(id);
	 }
	 
}
