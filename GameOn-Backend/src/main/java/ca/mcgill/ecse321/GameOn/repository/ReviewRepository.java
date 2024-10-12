package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Review findReviewById(int id);
}
