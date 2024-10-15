package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonById(int id);
}
