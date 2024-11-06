package ca.mcgill.ecse321.GameOn.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, String> {
    Manager findManagerByEmail(String email);
}
