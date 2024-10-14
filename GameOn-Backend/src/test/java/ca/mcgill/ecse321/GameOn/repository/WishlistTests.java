package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GameOn.model.Wishlist;

@SpringBootTest
public class WishlistTests {
    
    @Autowired
    private WishlistRepository wishlistRepo;
    
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        wishlistRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlist(){
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist = wishlistRepo.save(wishlist);
        int id = wishlist.getId();

        // Act
        Wishlist wishlist_from_DB = wishlistRepo.findWishlistById(id);

        // Assert
        assertNotNull(wishlist_from_DB);
        assertEquals(wishlist_from_DB.getId(),id);  
    }
}
