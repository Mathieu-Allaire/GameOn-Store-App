package ca.mcgill.ecse321.GameOn.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Cart findCartById(int id);
}
