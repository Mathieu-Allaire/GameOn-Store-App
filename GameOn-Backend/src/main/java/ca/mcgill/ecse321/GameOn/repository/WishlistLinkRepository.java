package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;

public interface WishlistLinkRepository extends CrudRepository<WishlistLink, Integer> {
    WishlistLink findWishlistLinkById(int id);
    
}
