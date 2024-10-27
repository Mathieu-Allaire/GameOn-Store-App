package ca.mcgill.ecse321.GameOn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.sql.Date;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepo;
    
    @Transactional
    public Person createPerson(String name, String email, String password){
        Date creationDate = Date.valueOf(LocalDate.now());
        Person person = new Person(1, name, email, password); // creationDate
        return personRepo.save(person);
    }

    public Person findPersonById(int pID) {
        return personRepo.findPersonById(pID);
    }
}
