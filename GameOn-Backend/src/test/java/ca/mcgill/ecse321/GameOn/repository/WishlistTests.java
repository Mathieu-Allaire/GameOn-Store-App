package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.sql.Date;
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
        wishlistRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlist(){
        // Create
        int aCardNum = 111; // customer id
        long millis = System.currentTimeMillis();
        Date aCardExpiryDate = new Date(millis);
        String aBillingAddress = "123 McGill Street";
        int aId = 5678; // wishlist ID
        
        // Step 1: Create and save the Customer without a wishlist
        Customer customer = new Customer(aCardNum, aCardExpiryDate, aBillingAddress);
        customer = customerRepo.save(customer);
        System.out.println("Saved customer: " + customer);
        
        // Step 2: Create the Wishlist and link it to the Customer
        Wishlist wishlist = new Wishlist(aId, customer);  // Now, create Wishlist
        wishlist = wishlistRepo.save(wishlist);  
        System.out.println("Saved wishlist: " + wishlist);
        System.out.println("id = : " + aId);
        // Step 3: Read Wishlist from DB
        Wishlist wishlist_from_DB = wishlistRepo.findWishlistById(wishlist.getId());
        System.out.println("Retrieved wishlist from DB: " + wishlist_from_DB);

        // Step 4: Print database state
        printDatabase();
        int cardNum = customer.getCardNum();
        String billingAddress = customer.getBillingAddress();
        Date cardExpiryDate = customer.getCardExpiryDate();
        // Assert
        assertNotNull(wishlist_from_DB, "Wishlist could not be saved and loaded from database.");
        assertEquals(wishlist_from_DB.getId(), wishlist.getId(), "Wishlist's 'ID' could not be saved and loaded from database.");
        assertNotNull(wishlist_from_DB.getWishlistCustomer(), "Wishlist's customer could not be saved and loaded from database.");
        assertEquals(
        Arrays.asList(wishlist_from_DB.getWishlistCustomer().getCardNum(), wishlist_from_DB.getWishlistCustomer().getBillingAddress(), wishlist_from_DB.getWishlistCustomer().getCardExpiryDate().toLocalDate()),
        Arrays.asList(cardNum, billingAddress, cardExpiryDate.toLocalDate()),
        "Wishlist's Customer fields do not match"
        );


        
}
     
    private void printDatabase() {
        // Retrieve all customers
        List<Customer> customers = (List<Customer>) customerRepo.findAll();
        System.out.println("=== Customers in Database ===");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        
        // Retrieve all wishlists
        List<Wishlist> wishlists = (List<Wishlist>) wishlistRepo.findAll();
        System.out.println("=== Wishlists in Database ===");
        for (Wishlist wishlist : wishlists) {
            System.out.println(wishlist);
        }
    }
}
