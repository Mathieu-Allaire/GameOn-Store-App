package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Category;


public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category findCategoryById(int id);
}
