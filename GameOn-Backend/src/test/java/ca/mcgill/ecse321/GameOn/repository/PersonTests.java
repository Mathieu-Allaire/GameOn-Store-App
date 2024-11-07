package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Cart;


@SpringBootTest
public class PersonTests {
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishlistLinkRepository wishlistLinkRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        personRepo.deleteAll();
        customerRepo.deleteAll();
        cartRepository.deleteAll();
        wishlistLinkRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {
        // Arrange
        String aName = "John Doe";
        String aEmail = "email@email.com";
        String aPassword = "password";

        // Create Customer
        Cart aCart = new Cart();

        // Create and save Customer first
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);
        Customer aCustomer = new Customer(111, aDate, "111 mcgill street", null); // Set cart as null initially
        aCustomer = customerRepo.save(aCustomer); // Save Customer first to assign an ID

        // Now link Customer and Cart
        aCart.setCustomer(aCustomer); // Set the Customer in Cart
        aCustomer.setCart(aCart); // Link Cart back to Customer

        // Save the Cart now that Customer ID is set
        aCart = cartRepository.save(aCart);
        customerRepo.save(aCustomer); // Optional: Save Customer again if bidirectional

        // Create Person
        Person aPerson = new Person(aEmail, aName, aPassword, aCustomer);
        aPerson = personRepo.save(aPerson);

        // Act
        Person personDB = personRepo.findPersonByEmail(aEmail);
        Customer customerDB = (Customer)personDB.getRole(0);

        // Assert
        assertNotNull(personDB);
        assertEquals(personDB.getName(), aPerson.getName());
        assertEquals(personDB.getEmail(), aPerson.getEmail());
        assertEquals(personDB.getPassword(), aPerson.getPassword());
        assertEquals(customerDB.getCardNum(), aCustomer.getCardNum());
        assertEquals(customerDB.getCardExpiryDate().toString(), aCustomer.getCardExpiryDate().toString());
        assertEquals(customerDB.getBillingAddress(), aCustomer.getBillingAddress());
    }
}
