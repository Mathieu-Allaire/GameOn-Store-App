package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ca.mcgill.ecse321.GameOn.model.Person;

public interface PersonRepository extends CrudRepository<Person, String> {
    Person findPersonByEmail(String email);

    @Query("SELECT p.email FROM Person p JOIN p.roles r WHERE r.id = :roleId")
    String findPersonEmailByRoleId(@Param("roleId") int roleId);

    @Query("SELECT r.id FROM Person p JOIN p.roles r WHERE p.email = :email")
    Integer findRoleIdsByPersonEmail(@Param("email") String email);
}
