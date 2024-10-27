package ca.mcgill.ecse321.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.service.PersonService;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * Creates a new person
     * @param personToCreate The person to create
     * @return The created person, including its ID
     */

    @PostMapping("/people")
    public Person createPerson(@RequestBody Person personToCreate) {
        Person createdPerson = personService.createPerson(
            personToCreate.getId(),
            personToCreate.getName(),
            personToCreate.getEmail(),
            personToCreate.getPassword());

        return personService.createPerson(personToCreate);
    }


    @GetMapping("/people/{pID}")
    public Person findPersonById(@PathVariable int pID) {
        Person createdPerson = personService.findPersonById(pID);
        return new personResponseDto(createdPerson);
    }
}   