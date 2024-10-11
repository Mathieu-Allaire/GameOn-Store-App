package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;


public interface SpecificGameRepository extends CrudRepository<SpecificGame, Integer> {
    SpecificGame findSpecificGameById(int id);
}
