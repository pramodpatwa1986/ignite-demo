package com.mywork.ignite.demo.node3;

import java.util.Optional;

import javax.cache.Cache;

import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mywork.ignite.demo.Person;

@Component
public class PersonRepository {

	@Autowired
	private Ignite ignite;
	
	
    public Person getPerson(Integer id) {
        return Optional.ofNullable(getPersonCache().get(id))
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }
    
    public void updatePerson(Integer id, Person person) {
    	System.out.println("Update person Service");
    	getPersonCache().put(id, person);
    }  

    public Cache<Integer, Person> getPersonCache() {    
    	return ignite.getOrCreateCache("PersonCache");      
    }

}
