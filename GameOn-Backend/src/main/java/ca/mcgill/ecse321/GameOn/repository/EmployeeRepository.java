package ca.mcgill.ecse321.GameOn.repository;
import ca.mcgill.ecse321.GameOn.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeById(int id);
}
