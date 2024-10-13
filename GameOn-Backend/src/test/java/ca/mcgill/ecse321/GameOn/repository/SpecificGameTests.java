package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;

@SpringBootTest
public class SpecificGameTests {
    @Autowired
    private SpecificGameRepository specGamerepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {


        specGamerepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadSpecificGame(){
        //Arrange
        / Create
        int aCardNum = 1234 // customer id
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 18);
        Date aCardExpiryDate = calendar.getTime(); // date January 18th 2020
        String aBillingAddress = "123 McGill Street";

        int aId = 5678
        Wishlist wishlist = new Wishlist(aId, aCardNum, aCardExpiryDate, aBillingAddress);
        Customer customer = new Customer(aCardNum,aCardExpiryDate,aBillingAddress, aId);

        //Save
        customer = customerRepo.save(customer);
        wishlist = wishlistRepo.save(wishlist);

        //Read
        WishList wishlist_from_DB = wishlistRepo.findWishlistbyid(aId);
        
        //Assert
        assertNotNull(wishlist_from_DB, "Wishlist could not be saved and loaded from database.");
        assertEquals(wishlist_from_DB.getId(), aCardNum, "Wishlist's 'ID' could not be saved and loaded from database.");
        assertNotNull(wishlist_from_DB.getWishlistCustomer(), "Wishlist's customer could not be saved and loaded from database.");
        assertEquals(wishlist_from_DB.getWishlistCustomer(), wishlist, "Wishlist's 'Customer' could not be saved and loaded from database.");
    }
    

}

