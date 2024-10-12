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
import ca.mcgill.ecse321.GameOn.model.WishList;
import ca.mcgill.ecse321.GameOn.repository.Customer;
import ca.mcgill.ecse321.GameOn.repository.WishList;


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
        int aCardNum = 1234 // customer id
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 18);
        Date aCardExpiryDate = calendar.getTime(); // date January 18th 2020
        String aBillingAddress = "123 McGill Street";

        int aId = 5678
        Wishlist wishlist = new Wishlist(aId, aCardNum, aCardExpiryDate, aBillingAddress);
        Customer customer = new Customer(aCardNum,aCardExpiryDate,aBillingAddress, wishlist);

        //Save
        customer = customerRepo.save(customer);
        wishlist = wishlistRepo.save(wishlist);

        //Read
        Customer customer_from_DB = customerRepo.findCustomerbyCardNum(customer);

        //Assert
        assertNotNull(customer_from_DB, "Customer could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCardNum(), aCardNum, "Customer's 'Card Number' could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCardExpiryDate(), aCardExpiryDate, "Customer's 'Card Expiry Date' could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getBillingAddress(), aBillingAddress, "Customer's 'Billing Address' could not be saved and loaded from database.");
        assertNotNull(customer_from_DB.getCustomerWishlist(), "Customer's wishlist could not be saved and loaded from database.");
        assertEquals(customer_from_DB.getCustomerWishlist(), wishlist, "Customer's 'Wishlist' could not be saved and loaded from database.");
    }
}
