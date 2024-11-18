package ca.mcgill.ecse321.GameOn.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.OrderClass;

public interface OrderRepository extends CrudRepository<OrderClass, Integer> {
    OrderClass findOrderById(int id);
    
}
