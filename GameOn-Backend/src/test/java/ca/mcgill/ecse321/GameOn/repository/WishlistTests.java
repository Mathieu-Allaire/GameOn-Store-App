package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Calendar;
import java.util.Date;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Wishlist;

@SpringBootTest
public class WishlistTests {
    
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private WishlistRepository wishlistRepo;
    @BeforeEach
    @AfterEach

    public void clearDatabase() {
        customerRepo.deleteAll();
        wishlistRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlist(){
        // Create
        int aCardNum = 1234; // customer id
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 18);
        Date aCardExpiryDate = calendar.getTime(); // date January 18th 2020
        String aBillingAddress = "123 McGill Street";

        int aId = 5678;
        
        Customer customer = new Customer(aCardNum, (java.sql.Date) aCardExpiryDate,aBillingAddress, aId);
        Wishlist wishlist = new Wishlist(aId, customer);

        //Save
        customer = customerRepo.save(customer);
        wishlist = wishlistRepo.save(wishlist);

        //Read
        Wishlist wishlist_from_DB = wishlistRepo.findWishlistById(aId);
        
        //Assert
        assertNotNull(wishlist_from_DB, "Wishlist could not be saved and loaded from database.");
        assertEquals(wishlist_from_DB.getId(), aCardNum, "Wishlist's 'ID' could not be saved and loaded from database.");
        assertNotNull(wishlist_from_DB.getWishlistCustomer(), "Wishlist's customer could not be saved and loaded from database.");
        assertEquals(wishlist_from_DB.getWishlistCustomer(), customer, "Wishlist's 'Customer' could not be saved and loaded from database.");
    }
}
