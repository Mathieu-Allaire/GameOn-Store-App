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
        int cardNum = 1234;
        long millis = System.currentTimeMillis();
        Date aCustomerDate = new Date(millis);
        String aCustomerAddress = "123 main st";

        Cart cart = new Cart();
        cartRepository.save(cart);

        Customer aCustomer = new Customer(cardNum, aCustomerDate, aCustomerAddress,cart);
        aCustomer = customerRepo.save(aCustomer);

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
