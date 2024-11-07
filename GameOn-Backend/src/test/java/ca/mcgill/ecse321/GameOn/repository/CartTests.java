package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;

@SpringBootTest
public class CartTests {
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CustomerRepository customerRepository;
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        cartRepo.deleteAll();
    }

    @Test
    public void testCreateCart(){
        //Arrange
        Cart aCart = new Cart();

        // Create and save Customer first
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);
        Customer aCustomer = new Customer(111, aDate, "111 mcgill street", null); // Set cart as null initially
        aCustomer = customerRepository.save(aCustomer); // Save Customer first to assign an ID

        // Now link Customer and Cart
        aCart.setCustomer(aCustomer); // Set the Customer in Cart
        aCustomer.setCart(aCart); // Link Cart back to Customer

        // Save the Cart now that Customer ID is set
        aCart = cartRepo.save(aCart);
        customerRepository.save(aCustomer); // Optional: Save Customer again if bidirectional
        //Act
        Cart result = cartRepo.findCartById(aCart.getId());

        //Assert
        assertNotNull(result);
        assertEquals(aCart.getId(), result.getId());

    }
}