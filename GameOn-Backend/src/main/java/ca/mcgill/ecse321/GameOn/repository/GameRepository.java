package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Game;


public interface GameRepository extends CrudRepository<Game, String> {
    Game findGameByname(String name);
}
