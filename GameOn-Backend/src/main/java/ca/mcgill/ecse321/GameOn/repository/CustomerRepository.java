package ca.mcgill.ecse321.GameOn.repository

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findCustomerbyCardNum(int cardNum);
}