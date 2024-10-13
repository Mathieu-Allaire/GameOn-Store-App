package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.GameRequest;

public interface GameRequestRepository extends CrudRepository<GameRequest, Integer> {
  GameRequest findGameRequestById(int id);
}
