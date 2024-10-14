package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testCreateAndReadCustomer() {
        // Arrange
        int aCardNumber = 1234;
        long millis = System.currentTimeMillis();
        Date aCustomerDate = new Date(millis);
        String aCustomerAddress = "123 main st";

        // Create Wishlist
        Wishlist aWishlist = new Wishlist();

        // Create Customer with no wishlist
        Customer aCustomer = new Customer(aCardNumber, aCustomerDate, aCustomerAddress, aWishlist);

        aWishlist = wishlistRepo.save(aWishlist);

        aCustomer = customerRepo.save(aCustomer);

        Long LongId = aCustomer.getId();
        int id = LongId.intValue();

        // Act
        Customer customerDB = customerRepo.findCustomerById(id);

        // Assert
        assertNotNull(customerDB);
        assertEquals(customerDB.getId(), aCustomer.getId());
        assertEquals(customerDB.getCardNum(), aCustomer.getCardNum());
        assertEquals(customerDB.getCardExpiryDate().toString(), aCustomer.getCardExpiryDate().toString());
        assertEquals(customerDB.getBillingAddress(), aCustomer.getBillingAddress());
        assertEquals(customerDB.getCustomerWishlist().getId(), aCustomer.getCustomerWishlist().getId());
    }        
}
