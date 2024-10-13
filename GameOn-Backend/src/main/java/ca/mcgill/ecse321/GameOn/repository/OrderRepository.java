package ca.mcgill.ecse321.GameOn.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findOrderById(int id);
    
}
