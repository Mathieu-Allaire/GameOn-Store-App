package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Wishlist;

@SpringBootTest
public class CustomerTests {
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
    public void testCreateAndReadCustomer(){
        // Create
        int aCardNum = 1111; // customer id
        long millis = System.currentTimeMillis();
        Date aCardExpiryDate = new Date(millis);
        String aBillingAddress = "123 McGill Street";
        Wishlist wishlist = new Wishlist();
        Customer customer = new Customer(aCardNum, aCardExpiryDate, aBillingAddress, wishlist);
        wishlist = wishlistRepo.save(wishlist);
        customer = customerRepo.save(customer);
        //wishlist = wishlistRepo.save(wishlist);

        //Read
        Customer customer_from_DB = customerRepo.findCustomerByCardNum(aCardNum);
        System.out.println("DB billing address = " + customer_from_DB.getCardExpiryDate() + " created billing address = " + aCardExpiryDate);

        //Assert
        assertNotNull(customer_from_DB, "Customer could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCardNum(), aCardNum, "Customer's 'Card Number' could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCardExpiryDate().toLocalDate(), aCardExpiryDate.toLocalDate(), "Customer's 'Card Expiry Date' could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getBillingAddress(), aBillingAddress, "Customer's 'Billing Address' could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCustomerWishlist(), wishlist, "Customer's 'Wishlist' could not be saved and loaded from database.");
    }
}
