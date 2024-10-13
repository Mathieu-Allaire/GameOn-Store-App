package ca.mcgill.ecse321.GameOn.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GameOn.model.Wishlist;


public interface WishlistRepository extends CrudRepository<Wishlist,Integer> {
    Wishlist findWishlistById(int id);
}