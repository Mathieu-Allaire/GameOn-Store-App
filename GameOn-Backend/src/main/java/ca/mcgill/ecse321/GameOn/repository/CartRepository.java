package ca.mcgill.ecse321.GameOn.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;

public interface CartRepository extends CrudRepository<Cart, Customer> {
    Cart findCartByCustomer(Customer customer);
}
